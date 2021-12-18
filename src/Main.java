
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static playerInfo[] playerInfo= new playerInfo[2];
    public static boolean randomMove=false;
    public static int whosTurn;
    public static Scanner scanner= new Scanner(System.in);


    public static void main(String[] args) throws FileNotFoundException {



        playerInfo[0]= new playerInfo();
        playerInfo[1]=new playerInfo();
        //AIvHuman();

       // AIvAI();

       // human2Human(0);



      generateStat();





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




    public static void AIvAI()
    {
        System.out.println("Enter First Move Giver(0/1):");
        int FirstMove=scanner.nextInt();
        System.out.println("Player 0 Heuristic:");
        playerInfo[0].heuristic=scanner.nextInt();
        System.out.println("Player 0 depth:");
        playerInfo[0].depth=scanner.nextInt();
        System.out.println("Player 1 Heuristic:");
        playerInfo[1].heuristic=scanner.nextInt();
        System.out.println("Player 1 depth:");
        playerInfo[1].depth=scanner.nextInt();

        Board b= new Board();
        boolean anotherMove;
        boolean gameFinished=false;
        int moveGiver=FirstMove;
        while (!gameFinished)
        {
            do {

                    b.PrintBoard();
                    System.out.println("Player "+moveGiver+" move:");

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





    public static void generateStat() throws FileNotFoundException {
        randomMove=true;//we'll randomize move ordering
        Random random= new Random();
        PrintWriter pw=new PrintWriter("Statistics.csv");
        pw.println("Player 0 Heuristic,Player 1 Heuristic,Player 0 Won,Player 1 Won, Drawn");


        for(int h0=1;h0<=6;h0++)
        {
            for(int h1=1;h1<=6;h1++)
            {


                pw.print(h0+","+h1);
                float p1Won=0,p2Won=0,drawn=0;

                for(int i=0;i<10;i++)
                {
                    //we'll also randomize the depths
                    playerInfo[0]= new playerInfo(1+random.nextInt(10),h0);
                    if(random.nextInt(2)==1)playerInfo[1]=new playerInfo(playerInfo[0].depth+random.nextInt(3),h1);
                    else playerInfo[1]=new playerInfo(Math.abs(playerInfo[0].depth-random.nextInt(3)),h1);
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

        pw.close();
    }

    public static int AIvAIForStat(int FirstMove)
    {
        Board board= new Board();
        boolean anotherMove;
        boolean gameFinished=false;
        int moveGiver=FirstMove;
        while (!gameFinished)
        {
            do {
                State n= new State(board,0,0);
                int binNo=n.AlphaBetaSearch(moveGiver);
                anotherMove=board.move(moveGiver,binNo);
                gameFinished=board.gameFinished();
            }while (anotherMove && (!gameFinished));
            moveGiver=(moveGiver+1)%2;
        }


        return Integer.compare(board.board[0][0], board.board[1][0]);

    }



}
