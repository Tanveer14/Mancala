
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static playerInfo[] playerInfo= new playerInfo[2];
    public static boolean randomMove=false;
    public static int whosTurn;


    public static void main(String[] args) throws FileNotFoundException {


   // AIvHuman();

        playerInfo[0]= new playerInfo(6,1);
        playerInfo[1]=new playerInfo(6,1);

        AIvAI(0);

       // human2Human(0);

       // AIvHuman();





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
                System.out.print("Player "+moveGiver+" move:");
                int binNo=scanner.nextInt();
                anotherMove=b.move(moveGiver,binNo);
                gameFinished=b.gameFinished();
            }while (anotherMove && (!gameFinished));
            moveGiver=(moveGiver+1)%2;
        }
    }

    public static void AIvHuman()
    {
        System.out.println("Enter Human Player No(0/1): ");
        Board b= new Board();
        Scanner scanner= new Scanner(System.in);
        int humanPlayer=scanner.nextInt();

        System.out.println("Enter who'll give first move(0/1): ");
        int firstMove=scanner.nextInt();
        boolean anotherMove;
        boolean gameFinished=false;
        int AIplayer=(humanPlayer+1)%2;

        System.out.println("Enter depth and Heuristic:");
        int depth=scanner.nextInt();
        int heuristic=scanner.nextInt();

        playerInfo[AIplayer]=new playerInfo(depth,heuristic);

        if(firstMove==humanPlayer)
        {
            do {
                b.PrintBoard();
                System.out.print("Player "+humanPlayer+" move:");
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
                int AIMove=n.AlphaBetaSearch(AIplayer);
                System.out.println("AI move: "+AIMove);
                anotherMove=b.move(AIplayer,AIMove);
                gameFinished=b.gameFinished();
            }while (anotherMove && (!gameFinished));

            if(gameFinished) break;

            do {
                b.PrintBoard();
                System.out.print("Player "+humanPlayer+" move:");
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


    public static void generateStat() throws FileNotFoundException {
        randomMove=true;
        PrintWriter pw=new PrintWriter("Statistics.csv");
        pw.println("Depth,Player 1 Heuristic,Player 2 Heuristic,Player 1 Won,Player 2 Won, Drawn");

        for(int depth=10;depth<=14;depth+=2)
        {

            for(int h1=1;h1<=6;h1++)
            {
                for(int h2=1;h2<=6;h2++)
                {
                    playerInfo[0]= new playerInfo(depth,h1);
                    playerInfo[1]=new playerInfo(depth,h2);
                    pw.print(depth+","+h1+","+h2);
                    float p1Won=0,p2Won=0,drawn=0;

                    for(int i=0;i<10;i++)
                    {
                        int result=AIvAIForStat(0);
                        if(result==1)p1Won++;
                        else if(result==-1)p2Won++;
                        else if(result==0)drawn++;
                    }

                    p1Won=p1Won*10;
                    p2Won=p2Won*10;
                    drawn=drawn*10;


                    pw.println(","+p1Won+","+p2Won+","+drawn);
                }
            }
        }

        pw.close();
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
            if(b.board[0][0]>b.board[1][0]) System.out.println("Player 0 won");
            else if (b.board[0][0]<b.board[1][0]) System.out.println("Player 1 won");
            else System.out.println("Drawn");

    }



    public static int AIvAIForStat(int FirstMove)
    {
        Board b= new Board();
        boolean anotherMove;
        boolean gameFinished=false;
        int moveGiver=FirstMove;
        while (!gameFinished)
        {
            do {


                State n= new State(b,0,0);

                int binNo=n.AlphaBetaSearch(moveGiver);

                anotherMove=b.move(moveGiver,binNo);
                gameFinished=b.gameFinished();
            }while (anotherMove && (!gameFinished));
            moveGiver=(moveGiver+1)%2;
        }


        return Integer.compare(b.board[0][0], b.board[1][0]);

    }



}
