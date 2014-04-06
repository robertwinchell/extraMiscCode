

mtype = { P, C };
mtype turn = P;
active proctype producer()
{
	(turn == P) ->
	printf("Produce\n");
	turn = C;
}

active proctype consumer()
{

	(turn == C) ->
	printf("Consume\n");
	turn = P;
	
}
