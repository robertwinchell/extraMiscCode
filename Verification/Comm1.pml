chan global = [0] of {bool};

proctype A() {
startA:	if
		:: skip;
		:: global!1;
		:: global!0;
	fi;
	goto startA
}

proctype B() {
bool rec;
startB:	if
		:: skip;
		:: global?rec;
	fi;
	goto startB
}

init{atomic{run A(); run B();}}
