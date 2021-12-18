public class Utility {
    public static int utility(int playerNo,Board board,int choice)
    {
        int w1=4,w2=0,w3=4,w4,w5,w6;
        if(choice==1) return (board.board[playerNo][0]-board.board[(playerNo+1)%2][0]);

        int mySide=0;
        int opponentSide=0;
        for(int i=1;i<board.board[playerNo].length;i++)
        {
            mySide+=board.board[playerNo][i];
            opponentSide+=board.board[(playerNo+1)%2][i];
        }

        if(choice==2)
        {

            return w1*(board.board[playerNo][0]-board.board[(playerNo+1)%2][0])+ w2*(mySide - opponentSide);
        }

        if(choice==3)
        {
            return w1*(board.board[playerNo][0]-board.board[(playerNo+1)%2][0])
                    + w2*(mySide - opponentSide)+w3*Main.playerInfo[playerNo].freeMoves;
        }


        if(choice==4)
        {
            w4=2;
            w6=3;
            return w1*(board.board[playerNo][0]-board.board[(playerNo+1)%2][0])
                    + w4*Main.playerInfo[playerNo].selectedBin +w6*board.totalCaptured[playerNo];
        }


        int closedToMyStorage=0;
        for(int i=1;i<board.board[playerNo].length;i++)
        {
            closedToMyStorage += Math.min(board.board[playerNo][i], i);

        }

        if(choice==5)
        {
            w5=2;
            return w1*(board.board[playerNo][0]-board.board[(playerNo+1)%2][0])
                    + w5*closedToMyStorage;//selected bin specifies how close the bin selected is to the storage
        }

        if(choice==6)
        {
            w6=3;
            return w1*(board.board[playerNo][0]-board.board[(playerNo+1)%2][0]) + w6*board.totalCaptured[playerNo];
        }

        else return 0;



    }
}
