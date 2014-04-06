/*LTL is still somewhat confusing to me but I hope to have it clarified during the exercise portion*/
	#define true 1
#define false 0
#define Aturn false
#define Bturn true

bool states[2];
bool t;
byte count;

active proctype A() {
	startA:
	states[0] = true;
	t = Bturn;
	(states[1] == false || t == Aturn);
	count++;
		   /* critical section */
	count--;
	states[0] = false;
	goto startA
}

never{
	count <= 1;
}

