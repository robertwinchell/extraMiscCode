/*I keep getting errors when the ints get to 40 or so, no syntax errors, I'm not sure why it is throwing that error*/
/*I think my syntax is sound but I'm not sure on channels quite yet*/
chan nums = [0] of {int}
int counter0 = 0;
int counter1 = 1;
proctype A()
{
if(true)
	::nums!0; 
	::nums!1
fi;
}
proctype B()
{
	do
	::nums?1->counter1++;
	::nums?0->counter0++;
	od;
}
init{atomic{A(); B();}}
