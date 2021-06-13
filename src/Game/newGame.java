package Game;

public class newGame {
	public newGame() {
		int N = 20;
		int M = (int) (Math.random() * (N - 1) + 1);
//		M = 20;
		OriginalIm k = new OriginalIm(M);
		PuzzleGame k2 = new PuzzleGame(M, k);
	}
}
