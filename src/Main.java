import java.util.Scanner;

public class Main {
    public static int maxDepth=4;

    public static void main(String[] args) {
	// write your code here


    AIvHuman(0,1);
    }

    public static void human2Human()
    {
        Board b= new Board();
        Scanner scanner= new Scanner(System.in);
        boolean anotherMove;
        boolean gameFinished=false;
        int moveGiver=0;
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
                node n= new node(b,0,0);
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
    }

}
