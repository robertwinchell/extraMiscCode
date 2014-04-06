#define true 1
#define false 0
#define Aturn false
#define Bturn true

bool x, y, t, progressA, progressB;

proctype A() {
startA:	x = true;
	t = Bturn;
	(y == false || t == Aturn);
	/* critical section */
	progressA=1;
	x = false;
	goto startA
}

proctype B() {
startB:	y = true;
	t = Aturn;
	(x == false || t == Bturn);
	/* critical section */
	progressB=1;
	y = false;
	goto startB
}

proctype monitor(){
startM:	progressA && progressB;
progress:	atomic{progressA=0; progressB=0;}
	goto startM
}

init { atomic {run A(); run B(); run monitor()} }
