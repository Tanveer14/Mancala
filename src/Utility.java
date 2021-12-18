public class Utility {
    public static int utility(int playerNo,Board board,int choice)
    {
        int w1=4,w2=1,w3=4,w4=3,w5=10,w6=10;
        if(choice==1) return w1*(board.board[playerNo][0]-board.board[(playerNo+1)%2][0]);

        int mySide=0;
        int opponentSide=0;
        for(int i=1;i<board.board[playerNo].length;i++)
        {
            mySide+=board.board[playerNo][i];
            opponentSide+=board.board[(playerNo+1)%2][i];
        }

        if(choice==2) return w1*(board.board[playerNo][0]-board.board[(playerNo+1)%2][0])+ w2*(mySide - opponentSide);

        if(choice==3) return w1*(board.board[playerNo][0]-board.board[(playerNo+1)%2][0])+ w2*(mySide - opponentSide)+w3*Main.playerInfo[playerNo].freeMoves;

        if(choice==4) return w1*(board.board[playerNo][0]-board.board[(playerNo+1)%2][0])
                +w2*(mySide - opponentSide)+w3*Main.playerInfo[playerNo].freeMoves
                + w4*Main.playerInfo[playerNo].selectedBin ;


        int closedToMyStorage=0;
        for(int i=1;i<board.board[playerNo].length;i++)
        {
            if(board.board[playerNo][i]<=i)closedToMyStorage+=board.board[playerNo][i];
            else closedToMyStorage+=i;

        }

        if(choice==5) return w1*(board.board[playerNo][0]-board.board[(playerNo+1)%2][0])
                +w2*(mySide - opponentSide)+w3*Main.playerInfo[playerNo].freeMoves
                + w4*Main.playerInfo[playerNo].selectedBin + w5*closedToMyStorage;

        else return 0;



    }
}
