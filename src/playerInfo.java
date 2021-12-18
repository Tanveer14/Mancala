public class playerInfo {

    int freeMoves;
    int capturedInThisMove;
    int selectedBin;
    int heuristic;
    int depth;
    playerInfo(int depth,int heuristic)
    {
        this.depth=depth;
        this.heuristic=heuristic;
    }
}
