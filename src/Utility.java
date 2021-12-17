public class Utility {
    public static int utility(int playerNo,Board board,int choice)
    {
        if(choice==1) return (board.board[playerNo][0]-board.board[(playerNo+1)%2][0]);
        else if(choice==2 ||choice==3)
        {
            int mySide=0;
            int opponentSide=0;
            for(int i=1;i<board.board.length;i++)
            {
                mySide+=board.board[playerNo][i];
                opponentSide+=board.board[(playerNo+1)%2][i];
            }
            if(choice==2) return 4*(board.board[playerNo][0]-board.board[(playerNo+1)%2][0])+ 2*(mySide - opponentSide);
            else return 4*(board.board[playerNo][0]-board.board[(playerNo+1)%2][0])+ 2*(mySide - opponentSide)+3*board.additionalMove;
        }
        else return 0;

    }
}
