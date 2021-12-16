import java.util.Scanner;

public class Main {
    public static int maxDepth=10;

    public static void main(String[] args) {
	// write your code here


    AIvHuman(0);
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
                int storageNo=scanner.nextInt();
                anotherMove=b.move(moveGiver,storageNo);
                gameFinished=b.gameFinished();
            }while (anotherMove && (!gameFinished));
            moveGiver=(moveGiver+1)%2;
        }
    }

    public static void AIvHuman(int humanPlayer)
    {
        Board b= new Board();
        Scanner scanner= new Scanner(System.in);
        boolean anotherMove;
        boolean gameFinished=false;
        int AIplayer=(humanPlayer+1)%2;
        int moveGiver=0;

        if(moveGiver==humanPlayer)
        {
            do {
                b.PrintBoard();
                System.out.print("Player "+(moveGiver+1)+" move:");
                int storageNo=scanner.nextInt();
                anotherMove=b.move(humanPlayer,storageNo);
                gameFinished=b.gameFinished();
            }while (anotherMove && (!gameFinished));//human move

        }


        while (!gameFinished)
        {

            do {
                b.PrintBoard();
                node n= new node(b,0,0);
                int AImove=n.MiniMaxDecision(AIplayer);
                System.out.println("AI move: "+AImove);
                anotherMove=b.move(AIplayer,AImove);
                gameFinished=b.gameFinished();
            }while (anotherMove && (!gameFinished));

            if(gameFinished) break;

            do {
                b.PrintBoard();
                System.out.print("Player "+(moveGiver+1)+" move:");
                int storageNo=scanner.nextInt();
                anotherMove=b.move(humanPlayer,storageNo);
                gameFinished=b.gameFinished();
            }while (anotherMove && (!gameFinished));//human move


        }

        b.PrintBoard();


    }



}
