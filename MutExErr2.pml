/* erroneous solution of mutex */

byte my_turn = 0;

proctype process1()
{
	if
	::my_turn==0 ->		my_turn++;
			assert (my_turn <= 1); /* critical section */
			my_turn = 0;
	fi
}

proctype process2()
{
	if
	::my_turn==0 ->		my_turn++;
			assert (my_turn <= 1); /* critical section */
			my_turn = 0;
	fi
}

init { atomic { run process1(); run process2() } }
