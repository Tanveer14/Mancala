import java.util.Scanner;

public class Main {
    public static int maxDepth=20;

    public static void main(String[] args) {
	// write your code here


    //AIvHuman(0,1);
        //AIvHuman(1,0);
        AIvAI(0);
    }

    public static void human2Human(int FirstMove)
    {
        Board b= new Board();
        Scanner scanner= new Scanner(System.in);
        boolean anotherMove;
        boolean gameFinished=false;
        int moveGiver=FirstMove;
        while (!gameFinished)
        {
            do {
                b.PrintBoard();
                System.out.print("Player "+(moveGiver+1)+" move:");
                int binNo=scanner.nextInt();
                anotherMove=b.move(moveGiver,binNo);
                gameFinished=b.gameFinished();
            }while (anotherMove && (!gameFinished));
            moveGiver=(moveGiver+1)%2;
        }
    }

    public static void AIvHuman(int humanPlayer,int firstMove)
    {
        Board b= new Board();
        Scanner scanner= new Scanner(System.in);
        boolean anotherMove;
        boolean gameFinished=false;
        int AIplayer=(humanPlayer+1)%2;

        if(firstMove==humanPlayer)
        {
            do {
                b.PrintBoard();
                System.out.print("Player "+(humanPlayer+1)+" move:");
                int binNo=scanner.nextInt();
                if(b.board[humanPlayer][binNo]!=0)
                {
                    anotherMove=b.move(humanPlayer,binNo);
                    gameFinished=b.gameFinished();
                }
                else
                {
                    System.out.println("Please select a non-zero storage");
                    anotherMove=true;
                }
            }while (anotherMove && (!gameFinished));//human move

        }


        while (!gameFinished)
        {

            do {
                b.PrintBoard();
                State n= new State(b,0,0);
                int AIMove=n.MiniMaxDecision(AIplayer);
                System.out.println("AI move: "+AIMove);
                anotherMove=b.move(AIplayer,AIMove);
                gameFinished=b.gameFinished();
            }while (anotherMove && (!gameFinished));

            if(gameFinished) break;

            do {
                b.PrintBoard();
                System.out.print("Player "+(humanPlayer+1)+" move:");
                int binNo=scanner.nextInt();
                if(b.board[humanPlayer][binNo]!=0)
                {
                    anotherMove=b.move(humanPlayer,binNo);
                    gameFinished=b.gameFinished();
                }
                else
                {
                    System.out.println("Please select a non-zero storage");
                    anotherMove=true;
                }
            }while (anotherMove && (!gameFinished));//human move
        }

        b.PrintBoard();
        if(b.board[AIplayer][0]>b.board[humanPlayer][0]) System.out.println("AI won");
        else if(b.board[AIplayer][0]<b.board[humanPlayer][0]) System.out.println("Human won");
        else System.out.println("Drawn");


    }

    public static void AIvAI(int FirstMove)
    {
        Board b= new Board();
        boolean anotherMove;
        boolean gameFinished=false;
        int moveGiver=FirstMove;
        while (!gameFinished)
        {
            do {
                b.PrintBoard();
                System.out.println("Player "+(moveGiver+1)+" move:");
                State n= new State(b,0,0);
                int binNo=n.AlphaBetaSearch(moveGiver);
                System.out.println(binNo);
                anotherMove=b.move(moveGiver,binNo);
                gameFinished=b.gameFinished();
            }while (anotherMove && (!gameFinished));
            moveGiver=(moveGiver+1)%2;
        }
        b.PrintBoard();
        if(b.board[0][0]>b.board[1][0]) System.out.println("Player 1 won");
        else if (b.board[0][0]<b.board[1][0]) System.out.println("Player 2 won");
        else System.out.println("Drawn");
    }



}
