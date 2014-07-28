
/*
 Rule102 -- a class within the Cellular Automaton Explorer. 
 Copyright (C) 2005  David B. Bahr (http://academic.regis.edu/dbahr/)

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 
 This class has been modified by RWinchell for the class assignment of CA class 2009
 */

package userRules;

import java.util.*;

import javax.swing.JLabel;

import cellularAutomata.CAPropertyReader;
import cellularAutomata.rules.templates.IntegerRuleTemplate;
import cellularAutomata.rules.util.RuleFolderNames;

/**
 * A rule which the social temperature for a given cell then checks its neighbors around it
 * to determine its next state.  It is very similar to the majority and minority wins\
 * with the addition that minorities can come back within a large concentration of 
 * the majority
 *
 * @author David Bahr and Zach Winchell
 */
public class ZachsRule extends IntegerRuleTemplate
{
    /**
     * A display name for this class. (This is not normally public, but
     * Rule102 is used frequently in the CA Explorer, and this is a handy
     * shortcut. When writing your own rule, I'd suggest making this variable
     * private.)
     */
    public static final String RULE_NAME = "Zach's 1st Rule";

    // a one line tooltip description for this rule
    private static final String TOOLTIP = "<html> <body><b>Rule 102.</b> Calculates the sum modulo N of a cell "
        + "and its neighbor to the right.</body></html>";

    // a description of property choices that give the best results for this
    // rule (e.g., which lattice, how many states, etc.)
    private static final String BEST_RESULTS = "<html> <body><b>Rule 102.</b>"
        + "<p> <b>For best results</b>, try a Square (8 neighbor) lattice with two "
        + "states, wrap around boundaries"
        + leftClickInstructions + rightClickInstructions + "</body></html>";
    
    //Temperature Variables I need to give each cell its temp
    private int width=100;
    private int height=100;
    
    private Random r = new Random();
    private static double[] socialForce = null;
    private double socialTemperature = r.nextDouble();
    
    private double[] temperature = null;
    private static int temp = 0;
    public static int numTimes = 1;
    
    private static double upDownNoiseThreshold = 0.0;
    private static double probabilityOfSpontaneousWeightGain = 0.002;
    

    // the number of states -- reset by the integerRule method
    private static int numStates = 2;
    private static int num = 1;


    /**
     * Create the rule corresponding to the number 102 (a la Wolfram) using the
     * given cellular automaton properties.
     * 
     * @param properties
     *            The cellular automaton properties which are used to pass
     *            information in and out of the Rule. May be null.
     */
    public ZachsRule(Properties properties)
    {
        super(properties);
        
        if(properties != null)
        { 
        	//makes the temp array according to size of grid
        width = Integer.parseInt(super.properties.getProperty(CAPropertyReader.CA_WIDTH));
        height = Integer.parseInt(super.properties.getProperty(CAPropertyReader.CA_HEIGHT));
         temperature= new double[width*height];
        }
    }

    /**
     * Calculates the social temperature of a cell based on its neighbors and itself
     * list of neighbors.
     * 
     * @param cell
     *            The value of the cell being updated.
     * @param neighbors
     *            The value of the neighbors.
     * @param generation
     *            The current generation of the CA.
     * 
     * @return A new state for the cell.
     */
    
    
    protected int integerRule(int cell, int[] neighbors, int numStates,
            int generation)
        {
    	//assigns a temp value to each array element and checks
    	//if it is the array length to set it back to 0
    	
    	if(numTimes == temperature.length)
		{
			numTimes = 0;
		}
    		temperature[numTimes]=r.nextDouble()*2.0;
    		 
    		double d = temperature[numTimes];
    				
            if(socialForce == null || this.numStates != numStates)
            {
                // so we can use this elsewhere
                this.numStates = numStates;

                // recreate the social forces
                socialForce = new double[numStates];
                for(int i = 1; i < socialForce.length; i++)
                {
                    socialForce[i] = r.nextDouble()*.1;
                }
            }

            // store how many cells have each state
            int[] numberOfEachState = new int[numStates];

            // initialize
            for(int i = 0; i < numStates; i++)
            {
                numberOfEachState[i] = 0;
            }

            // figure out how many cells have each state
            for(int i = 0; i < neighbors.length; i++)
            {
                int state = neighbors[i];

                numberOfEachState[state]++;
            }

            // the value that is returned
            int cellValue = cell;

            // if the temp is 0, then the cell's all keep their current value (no
            // change -- see my online lecture notes)
            if(socialTemperature != 0.0)
            {
                // get probability (percent) of each state, including the social
                // force and temperature. (We divide by the partition function
                // later.)
                double[] prob = new double[numStates];
                for(int i = 0; i < numStates; i++)
                {
                    // a threshold rule version
                    // prob[i] = Math.pow(Math.E, socialForce[i] /
                    // socialTemperature)
                    // * Math.pow(Math.atan((((double) numberOfEachState[i])
                    // / (double) neighbors.length) - 2.0)+(Math.PI/2.0), 1.0 /
                    // socialTemperature);

                    // adapted to fit Zachs Social Rule
                	
           	
                    prob[i] = Math.pow(Math.E, socialForce[i] / d)
                        * Math.pow(((double) numberOfEachState[i])
                            / (double) neighbors.length, 1.0 / d);
                    
                   
                }
                numTimes++;

                // calculate the partition function
                double z = 0.0;
                for(int i = 0; i < numStates; i++)
                {
                    z += prob[i];
                }

                // now divide each probability by the partition function (so scaled
                // properly)
                for(int i = 0; i < numStates; i++)
                {
                    prob[i] /= z;
                }

                // get cumulative probability of each state
                double[] cumProb = new double[numStates];
                cumProb[0] = prob[0];
                for(int i = 1; i < numStates; i++)
                {
                    cumProb[i] = cumProb[i - 1] + prob[i];
                }

                // Now get a random number between 0 and 1
                double randomNumber = r.nextDouble();

                // use the random number to choose a state (j is the state)
                int j = 0;
                while((randomNumber > cumProb[j]) && (j < numStates))
                {
                    j++;
                }
                num++;
                cellValue = j;
            }

            // now bias the result so that even after choosing based on their
            // social network, the individual will sometimes increase their
            // weight regardless of their neighbors. The rational is that
            // environmental and biological factors make it more likely for the
            // individual to gain weight. Make this happen with some (presumably
            // low) probability. This is essentially a "biased noise".
            if(r.nextDouble() < probabilityOfSpontaneousWeightGain)
            {
                // only increase if not already at the maximum
                if(cellValue < numStates - 1)
                {
                    cellValue++;
                }
            }

            // now add a random noise that moves the cell value up or down only one
            // unit. In other words, with a certain probability, the cell increases
            // or decreases weight by one unit.
            if(r.nextDouble() < upDownNoiseThreshold)
            {
                // change the state randomly
                if(r.nextBoolean())
                {
                    // only increase if not already at the maximum
                    if(cellValue < numStates - 1)
                    {
                        cellValue += 1;
                    }
                }
                else
                {
                    // only decrease if not already at the minimum
                    if(cellValue > 0)
                    {
                        cellValue -= 1;
                    }
                }
            }
            return cellValue;
        }
    /**
     * A brief description (written in HTML) that describes what parameters will
     * give best results for this rule (which lattice, how many states, etc).
     * The description will be displayed on the properties panel. Using html
     * permits line breaks, font colors, etcetera, as described in HTML
     * resources. Regular line breaks will not work.
     * <p>
     * Recommend starting with the title of the rule followed by "For best
     * results, ...". See Rule 102 for an example.
     * 
     * @return An HTML string describing how to get best results from this rule.
     *         May be null.
     */
    public String getBestResultsDescription()
    {
        return BEST_RESULTS;
    }

    /**
     * When displayed for selection, the rule will be listed under specific
     * folders specified here. The rule will always be listed under the "All
     * rules" folder. And if the rule is contributed by a user and is placed in
     * the userRules folder, then it will also be shown in a folder called "User
     * rules". Any strings may be used; if the folder does not exist, then one
     * will be created with the specified name. If the folder already exists,
     * then that folder will be used.
     * <p>
     * By default, this returns null so that the rule is only placed in the
     * default folder(s).
     * <p>
     * Child classes should override this method if they want the rule to appear
     * in a specific folder. The "All rules" and "User rules" folder are
     * automatic and do not need to be specified; they are always added.
     * 
     * @return A list of the folders in which rule will be displayed for
     *         selection. May be null.
     */
    public String[] getDisplayFolderNames()
    {
        String[] folders = {RuleFolderNames.INSTRUCTIONAL_FOLDER,
            RuleFolderNames.PRETTY_FOLDER};

        return folders;
    }

    /**
     * A brief one or two-word string describing the rule, appropriate for
     * display in a drop-down list.
     * 
     * @return A string no longer than 15 characters.
     */
    public String getDisplayName()
    {
        return RULE_NAME;
    }

    /**
     * A brief description (written in HTML) that describes this rule. The
     * description will be displayed as a tooltip. Using html permits line
     * breaks, font colors, etcetera, as described in HTML resources. Regular
     * line breaks will not work.
     * 
     * @return An HTML string describing this rule.
     */
    public String getToolTipDescription()
    {
        return TOOLTIP;
    }
}
