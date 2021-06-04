package Game;

public class newGame {
	public newGame() {
		int N = 8;
		int M = (int) (Math.random() * (N - 1) + 1);
		new PuzzleGame(M);
		new OriginalIm(M);
	}
}
