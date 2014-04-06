/* incrementer problem */
byte my_number = 0;
proctype process1()
{
	start1: /*less than 255 add 1 */
	if
		::my_number < 255 -> my_number++;
		
	fi;
	progress:
	goto start1
}
proctype process2()
{
	start2: /* greater than 0 add one */
	if
		::my_number > 0 -> my_number--;
		
	fi;
	progress:
	goto start2
}
init { run process1(); run process2()  }
