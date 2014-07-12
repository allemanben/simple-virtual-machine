package vm;

import static vm.Bytecode.BR;
import static vm.Bytecode.BRF;
import static vm.Bytecode.CALL;
import static vm.Bytecode.GLOAD;
import static vm.Bytecode.GSTORE;
import static vm.Bytecode.HALT;
import static vm.Bytecode.IADD;
import static vm.Bytecode.ICONST;
import static vm.Bytecode.ILT;
import static vm.Bytecode.IMUL;
import static vm.Bytecode.ISUB;
import static vm.Bytecode.LOAD;
import static vm.Bytecode.PRINT;
import static vm.Bytecode.RET;

public class Test {
	static int[] hello = {
		ICONST, 1,
		ICONST, 2,
		IADD,
		PRINT,
		HALT
	};

	static int[] loop = {
	// .GLOBALS 2; N, I
	// N = 10						ADDRESS
			ICONST, 10,				// 0
			GSTORE, 0,				// 2
	// I = 0
			ICONST, 0,				// 4
			GSTORE, 1,				// 6
	// WHILE I<N:
	// START (8):
			GLOAD, 1,				// 8
			GLOAD, 0,				// 10
			ILT,					// 12
			BRF, 24,				// 13
	//     I = I + 1
			GLOAD, 1,				// 15
			ICONST, 1,				// 17
			IADD,					// 19
			GSTORE, 1,				// 20
			BR, 8,					// 22
	// DONE (24):
	// PRINT "LOOPED "+N+" TIMES."
			HALT					// 24
	};

	static int[] factorial = {
//.def fact: ARGS=1, LOCALS=0		ADDRESS
//	IF N < 2 RETURN 1
			LOAD, -4,				// 0
			ICONST, 2,				// 2
			ILT,					// 4
			BRF, 10,				// 5
			ICONST, 1,				// 7
			RET,					// 9
//CONT:
//	RETURN N * FACT(N-1)
			LOAD, -4,				// 10
			LOAD, -4,				// 12
			ICONST, 1,				// 14
			ISUB,					// 16
			CALL, 0, 1,				// 17
			IMUL,					// 20
			RET,					// 21
//.DEF MAIN: ARGS=0, LOCALS=0
// PRINT FACT(10)
			ICONST, 5,				// 22    <-- MAIN METHOD!
			CALL, 0, 1,				// 24
			PRINT,					// 27
			HALT					// 28
	};
	
	static int[] tailRecursiveFactorial = {
		//.DEF FACT(X, R)
		// IF X < 2 RETURN R
					LOAD, -5,				// 0
					ICONST, 2,				// 2
					ILT,					// 4
					BRF, 10,				// 5
					LOAD, -4,				// 7
					RET,					// 9
		//CONT:
		// RETURN FACT(X-1, X*R)
					LOAD, -5,				// 10
					ICONST, 1,				// 12
					ISUB,					// 14
					LOAD, -5,				// 15
					LOAD, -4,				// 17
					IMUL,					// 19
					CALL, 0, 2,				// 20
					RET,					// 23
		//.DEF MAIN: ARGS=0, LOCALS=0
		// PRINT FACT(10, 1)
					ICONST, 5,             // 24	<-- MAIN METHOD!
					ICONST, 1,				// 26
					CALL, 0, 2,				// 28
					PRINT,					// 31
					HALT					// 32
			};

	public static void main(String[] args) {
		VM vm = new VM(loop, 0, 0);
		vm.trace = true;
		vm.exec();
	}
}
