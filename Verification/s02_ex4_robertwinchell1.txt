mtype = { P, C };
mtype turn = P;
active proctype producer()
{
	wait: 
			turn == P;
				assert(turn == P)
				printf("Produce\n");
				turn = C;
}
active proctype consumer()
{
	wait: 
			turn == C;
				assert(turn == C)
				printf("Consume\n");
				turn = P;
}
init{atomic {run producer(); run consumer()}}