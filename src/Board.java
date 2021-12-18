import java.util.Arrays;

public class Board {
    int[][] board = new int[2][7];
    int[] totalCaptured;
    int additionalMove;//keeps the record if when the board is produced, an additional move earned
    Board()
    {
        Arrays.fill(board[0],4);
        Arrays.fill(board[1],4);
        board[0][0]=0;
        board[1][0]=0;
        additionalMove=0;
        totalCaptured=new int[2];

    }

    void PrintBoard()
    {
        System.out.println("P1  1\t2\t3\t4\t5\t6");
        System.out.println("\t----------------------");
        System.out.print("\t");
        for(int i=1;i<board[1].length;i++) System.out.print(board[1][i]+"\t");//player 2 data
        System.out.println();
        System.out.println(board[1][0]+"\t\t\t\t\t\t\t"+board[0][0]);
        System.out.print("\t");
        for(int i=board[1].length-1;i>0;i--) System.out.print(board[0][i]+"\t");//player 1 data
        System.out.println();
        System.out.println("\t----------------------");
        System.out.println("\t6\t5\t4\t3\t2\t1\tP0");
        System.out.println();
    }
    
    boolean move(int playerNo,int storageNo)//returned value indicates if the player should give another move
    {
        int opposition=(playerNo+1)%2;
        boolean anotherMove = false;
        int totalBeads=board[playerNo][storageNo];
        board[playerNo][storageNo]=0;
        while (totalBeads>0)
        {
            for(int i=storageNo-1;i>=0;i--)//in player's own side
            {
                board[playerNo][i]++;
                totalBeads--;
                if(totalBeads==0)
                {
                    if(i==0)anotherMove= true;//if the last bead at the player's own mancala then extra move
                    else if(board[playerNo][i]==1 && board[opposition][7-i]>0)//if the last bead in player's side
                        // and that is the only one and opposite side has non-zero beads
                    {
                        totalCaptured[playerNo]=(1+board[opposition][7-i]);
                        board[playerNo][0]+=(1+board[opposition][7-i]);
                        board[opposition][7-i]=0;
                        board[playerNo][i]=0;
                    }
                    break;
                }
            }

            if(totalBeads>0)//in opponent's side
            {
                for(int i=board[1].length-1;i>0;i--)
                {
                    board[opposition][i]++;
                    totalBeads--;
                    if(totalBeads==0)
                    {
                        break;
                    }
                }
            }
            storageNo=7;//set storage no to 7 so that
            // at next iteration of while loop, it starts from P1 storage 7-1=6
        }


        return anotherMove;
    }

    boolean gameFinished()
    {

        for(int player=0;player<2;player++)
        {
            int totalBeads=0;
            for(int i=1;i<board[player].length;i++)
            {
                totalBeads+=board[player][i];
            }
            if(totalBeads==0)
            {
                int opponent=(player+1)%2;
                for(int i=1;i<board[opponent].length;i++)
                {
                    board[opponent][0]+=board[opponent][i];
                    board[opponent][i]=0;
                }
                return true;
            }
        }
        return false;
    }


}
