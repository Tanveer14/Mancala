import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class State {
    //cloning 2D array Arrays.stream(this.boardPositions).map(int[]::clone).toArray(int[][]::new)
    Board board;
    int depth;
    int moveStorage;
    boolean anotherMove;

    State(Board board,int depth, int moveStorage)
    {
        this.board=new Board();
        this.board.board= Arrays.stream(board.board).map(int[]::clone).toArray(int[][]::new);
        this.depth=depth;
        this.moveStorage=moveStorage;
        this.anotherMove=false;
    }

    int utility(int playerNo)
    {
        return Utility.utility(playerNo,board,Main.playerInfo[playerNo].heuristic);
    }

    int AlphaBetaSearch(int playerNo)
    {
        //State result=new State(board,0,0);
        Main.whosTurn=playerNo;
        ActionUtilityPair action;
        action=MaxValue(playerNo,false,-Integer.MAX_VALUE,Integer.MAX_VALUE);
        return action.Action;
    }


    ActionUtilityPair MaxValue(int playerNo,boolean anotherMove,int alpha,int beta)//with alpha beta pruning
    {
        if(board.gameFinished()|| ( (depth>=Main.playerInfo[Main.whosTurn].depth)&& !anotherMove))
        {
            return new ActionUtilityPair(moveStorage,utility(Main.whosTurn));
        }
        ActionUtilityPair v=new ActionUtilityPair(0,-Integer.MAX_VALUE);


        ArrayList<Integer> possibleMoves=new ArrayList<>();
        for(int i=1;i<=6;i++)
        {
            possibleMoves.add(i);
        }
        if(Main.randomMove) Collections.shuffle(possibleMoves);

        for (int i:possibleMoves) {
            if(board.board[playerNo][i]>0)
            {


                State result;
                if(depth==0 )
                {
                    result=new State(board,1,i);
                    Main.playerInfo[playerNo].selectedBin=i;
                    Main.playerInfo[playerNo].freeMoves=0;
                }
                else
                {
                    result= new State(board,depth,moveStorage);
                    if(!anotherMove) result.depth++;
                }


                anotherMove=result.board.move(playerNo,i);


                if(anotherMove)
                {

                    if(depth==1)Main.playerInfo[playerNo].freeMoves++;
                    v=Max(result.MaxValue(playerNo,true,alpha,beta),v);


                }
                else
                {
                    if(depth==1)Main.playerInfo[playerNo].captured=result.board.totalCaptured[playerNo];
                    v=Max(result.MinValue((playerNo+1)%2,false,alpha,beta),v);
                }

                if(v.Utility>=beta) return v;
                alpha=Math.max(alpha,v.Utility);

            }
        }



        return v;
    }


    ActionUtilityPair MinValue(int playerNo,boolean anotherMove,int alpha,int beta)//with alpha beta pruning
    {
        if(board.gameFinished()|| ( (depth>=Main.playerInfo[Main.whosTurn].depth)&& !anotherMove))
        {
            return new ActionUtilityPair(moveStorage,utility(Main.whosTurn));
        }
        ActionUtilityPair v=new ActionUtilityPair(0,Integer.MAX_VALUE);

        for(int i=1;i<board.board[0].length;i++)
        {
            if(board.board[playerNo][i]>0)
            {

                State result;
                if(depth==0 )
                {
                    result=new State(board,1,i);
                    Main.playerInfo[playerNo].selectedBin=i;
                    Main.playerInfo[playerNo].freeMoves=0;
                }
                else
                {
                    result= new State(board,depth,moveStorage);
                    if (!anotherMove)result.depth++;
                }


                anotherMove=result.board.move(playerNo,i);


                if (anotherMove)
                {
                    v=Min(result.MinValue(playerNo,true,alpha,beta),v);

                }
                else
                {
                    v=Min(result.MaxValue((playerNo+1)%2,false,alpha,beta),v);

                }

                if(v.Utility<=alpha) return v;
                beta=Math.min(beta,v.Utility);
            }
        }
        return v;
    }




    ActionUtilityPair Max(ActionUtilityPair ap1,ActionUtilityPair ap2)
    {
        if(ap1.Utility> ap2.Utility) return ap1;
        else return ap2;
    }

    ActionUtilityPair Min(ActionUtilityPair ap1,ActionUtilityPair ap2)
    {
        if(ap1.Utility< ap2.Utility) return ap1;
        else return ap2;
    }






    int MiniMaxDecision(int playerNo)
    {
        //find all the moves
        //find the move that maximizes the utility
        State result=new State(board,0,0);

        ActionUtilityPair action = result.MaxValue(playerNo,false);
        /*
        ActionUtilityPair action = new ActionUtilityPair(0,-Integer.MAX_VALUE);

        int actionSt=0;
        ActionUtilityPair AfterMove;
        for(int i=1;i<board.board[0].length;i++)
        {
            if(board.board[playerNo][i]>0)//consider only for non-zero storage
            {
                 result=new State(board,1,i);

                boolean anotherMove=result.board.move(playerNo,i);
                //call MaxValue for this State if anotherMove true


                if(anotherMove)
                {
                    result.board.additionalMove++;
                    AfterMove=result.MaxValue(playerNo,true);


                }
                else AfterMove=result.MinValue(playerNo,false);

                if(AfterMove.Utility> action.Utility)
                {
                    actionSt=i;
                    action=AfterMove;
                }


            }
        }

         */
        //System.out.println(max);

        return action.Action;
    }

    ActionUtilityPair MaxValue(int playerNo,boolean anotherMove)
    {
        if(board.gameFinished()|| ( (depth>=Main.playerInfo[playerNo].depth)&& !anotherMove))
        {
            return new ActionUtilityPair(moveStorage,utility(playerNo));
        }//here is the problem
        ActionUtilityPair v=new ActionUtilityPair(0,-Integer.MAX_VALUE);

        for(int i=1;i<board.board[0].length;i++)
        {
            if(board.board[playerNo][i]>0)
            {
                State result;
                if(depth==0 ) result=new State(board,1,i);
                else
                {
                    result= new State(board,depth,moveStorage);
                    if(!anotherMove) result.depth++;
                }

                anotherMove=result.board.move(playerNo,i);

                if(anotherMove)
                {
                    result.board.additionalMove++;
                    v=Max(result.MaxValue(playerNo,true),v);
                }
                else
                {
                    v=Max(result.MinValue(playerNo,false),v);
                }

            }
        }



        return v;
    }

    ActionUtilityPair MinValue(int playerNo,boolean anotherMove)
    {
        if(board.gameFinished()|| ( (depth>=Main.playerInfo[playerNo].depth)&& !anotherMove))
        {
            //System.out.println(moveStorage +" : "+utility(playerNo));
            return new ActionUtilityPair(moveStorage,utility(playerNo));
        }
        ActionUtilityPair v=new ActionUtilityPair(0,Integer.MAX_VALUE);

        for(int i=1;i<board.board[0].length;i++)
        {
            if(board.board[playerNo][i]>0)
            {
                //Board b= new Board();
                //b.board= Arrays.stream(this.board.board).map(int[]::clone).toArray(int[][]::new);

                State result;
                if(depth==0 ) result=new State(board,1,i);
                else
                {
                    result= new State(board,depth,moveStorage);
                    if (!anotherMove)result.depth++;
                }


                anotherMove=result.board.move(playerNo,i);


                if (anotherMove)
                {
                    result.board.additionalMove++;
                    v=Min(result.MinValue(playerNo,true),v);

                }
                else
                {
                    v=Min(result.MaxValue(playerNo,false),v);

                }


            }
        }
        return v;
    }
}
