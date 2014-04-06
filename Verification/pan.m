#define rand	pan_rand
#if defined(HAS_CODE) && defined(VERBOSE)
	#ifdef BFS_PAR
		bfs_printf("Pr: %d Tr: %d\n", II, t->forw);
	#else
		cpu_printf("Pr: %d Tr: %d\n", II, t->forw);
	#endif
#endif
	switch (t->forw) {
	default: Uerror("bad forward move");
	case 0:	/* if without executable clauses */
		continue;
	case 1: /* generic 'goto' or 'skip' */
		IfNotBlocked
		_m = 3; goto P999;
	case 2: /* generic 'else' */
		IfNotBlocked
		if (trpt->o_pm&1) continue;
		_m = 3; goto P999;

		 /* PROC consumer */
	case 3: /* STATE 1 - s02_ex4_robertwinchell.pml:15 - [((turn==C))] (0:0:0 - 1) */
		IfNotBlocked
		reached[1][1] = 1;
		if (!((now.turn==1)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 4: /* STATE 2 - s02_ex4_robertwinchell.pml:16 - [printf('Consume\\n')] (0:0:0 - 1) */
		IfNotBlocked
		reached[1][2] = 1;
		Printf("Consume\n");
		_m = 3; goto P999; /* 0 */
	case 5: /* STATE 3 - s02_ex4_robertwinchell.pml:17 - [turn = P] (0:0:1 - 1) */
		IfNotBlocked
		reached[1][3] = 1;
		(trpt+1)->bup.oval = now.turn;
		now.turn = 2;
#ifdef VAR_RANGES
		logval("turn", now.turn);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 6: /* STATE 4 - s02_ex4_robertwinchell.pml:19 - [-end-] (0:0:0 - 1) */
		IfNotBlocked
		reached[1][4] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */

		 /* PROC producer */
	case 7: /* STATE 1 - s02_ex4_robertwinchell.pml:7 - [((turn==P))] (0:0:0 - 1) */
		IfNotBlocked
		reached[0][1] = 1;
		if (!((now.turn==2)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 8: /* STATE 2 - s02_ex4_robertwinchell.pml:8 - [printf('Produce\\n')] (0:0:0 - 1) */
		IfNotBlocked
		reached[0][2] = 1;
		Printf("Produce\n");
		_m = 3; goto P999; /* 0 */
	case 9: /* STATE 3 - s02_ex4_robertwinchell.pml:9 - [turn = C] (0:0:1 - 1) */
		IfNotBlocked
		reached[0][3] = 1;
		(trpt+1)->bup.oval = now.turn;
		now.turn = 1;
#ifdef VAR_RANGES
		logval("turn", now.turn);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 10: /* STATE 4 - s02_ex4_robertwinchell.pml:10 - [-end-] (0:0:0 - 1) */
		IfNotBlocked
		reached[0][4] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */
	case  _T5:	/* np_ */
		if (!((!(trpt->o_pm&4) && !(trpt->tau&128))))
			continue;
		/* else fall through */
	case  _T2:	/* true */
		_m = 3; goto P999;
#undef rand
	}

