public class playerInfo {

    int freeMoves;
    int selectedBin;
    int heuristic;
    int depth;
    int captured;
    playerInfo(int depth,int heuristic)
    {
        this.depth=depth;
        this.heuristic=heuristic;
    }
    playerInfo()
    {
        depth=0;
        heuristic=0;
    }
}
