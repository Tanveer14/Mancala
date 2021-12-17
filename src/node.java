
import java.util.Arrays;

public class node {
    //cloning 2D array Arrays.stream(this.boardPositions).map(int[]::clone).toArray(int[][]::new)
    Board board;
    int depth;
    int moveStorage;
    boolean anotherMove;
    node(Board board,int depth, int moveStorage)
    {
        this.board=new Board();
        this.board.board=Arrays.stream(board.board).map(int[]::clone).toArray(int[][]::new);
        this.depth=depth;
        this.moveStorage=moveStorage;
        this.anotherMove=false;
    }
    /*
    boolean isTerminal()
    {
        return board.gameFinished() || (depth>=Main.maxDepth);
    }

     */
    int utility(int playerNo)
    {
        return (board.board[playerNo][0]-board.board[(playerNo+1)%2][0]);
    }

    int MiniMaxDecision(int playerNo)
    {
        //find all the moves
        //find the move that maximizes the utility

        int action = 0;
        int max=-Integer.MAX_VALUE;
        int valueAfterMove=0;
        for(int i=1;i<board.board[0].length;i++)
        {
           if(board.board[playerNo][i]>0)//consider only for non-zero storage
            {
                node result=new node(board,1,i);

                boolean anotherMove=result.board.move(playerNo,i);
                //call MaxValue for this node if anotherMove true


                if(anotherMove)
                {
                    result.board.additionalMove++;
                    valueAfterMove=result.MaxValue(playerNo,true);


                }
                else valueAfterMove=result.MinValue(playerNo,false);

                if(valueAfterMove>max)
                {
                    max=valueAfterMove;
                    action=i;

                }


            }
        }

        //System.out.println(max);

        return action;
    }

    int MaxValue(int playerNo,boolean anotherMove)
    {
        if(board.gameFinished()|| ( (depth>=Main.maxDepth)&& !anotherMove))
        {
            return utility(playerNo);
        }//here is the problem
        int v=-Integer.MAX_VALUE;

        for(int i=1;i<board.board[0].length;i++)
        {
            if(board.board[playerNo][i]>0)
            {
                Board b= new Board();
                b.board= Arrays.stream(this.board.board).map(int[]::clone).toArray(int[][]::new);

                node result=new node(b,depth,i);
                if(!anotherMove) result.depth++;
                anotherMove=result.board.move(playerNo,i);


                if(anotherMove)
                {
                    result.board.additionalMove++;
                    v=Math.max(v,result.MaxValue(playerNo,true));

                }
                else
                {
                    v=Math.max(v,result.MinValue(playerNo,false));
                }



            }
        }

        return v;
    }

    int MinValue(int playerNo,boolean anotherMove)
    {
        if(board.gameFinished()|| ( (depth>=Main.maxDepth)&& !anotherMove))
        {
            return utility(playerNo);
        }
        int v=Integer.MAX_VALUE;

        for(int i=1;i<board.board[0].length;i++)
        {
            if(board.board[playerNo][i]>0)
            {
                Board b= new Board();
                b.board= Arrays.stream(this.board.board).map(int[]::clone).toArray(int[][]::new);

                node result=new node(b,depth,i);
                if (!anotherMove)result.depth++;
                anotherMove=result.board.move(playerNo,i);


                if (anotherMove)
                {
                    result.board.additionalMove++;
                    v=Math.min(v,result.MinValue(playerNo,true));

                }
                else
                {
                    v=Math.min(v,result.MaxValue(playerNo,false));

                }


            }
        }
        return v;
    }
}
