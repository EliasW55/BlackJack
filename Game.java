import java.util.ArrayList;
import java.util.Scanner;
public class Game
{
    
    private static int AceCounter;
    private static ArrayList<Card> hand;
    private static int handvalue;
    private static String name;
    public static void main(String[] args){
        System.out.println("What is your name?");
        Scanner scan = new Scanner(System.in);
        name = scan.nextLine();
        System.out.println("Hello, "+name + "!");
            Deck deck = new Deck();
            deck.shuffle();
            AceCounter=0;
            Dealer dealer = new Dealer(deck);
            ArrayList<Card> hand = new ArrayList<>();
            hand.add(deck.drawCard());
            hand.add(deck.drawCard());
            System.out.println("Here is your hand: ");
            System.out.println(hand);
            int handvalue = calcHandValue(hand);
            System.out.println("The dealer is showing: ");
            dealer.showFirstCard();
            if(hasBlackJack(handvalue) && dealer.hasBlackJack())
            {
                Push();
            }
            else if(hasBlackJack(handvalue))
            {
                System.out.println("You have BlackJack.");
                Win();
            }
            else if(dealer.hasBlackJack())
            {
                System.out.println("Here is the dealer's hand:");
                dealer.showHand();
                Lose();
            }
            else
            {
                
                    System.out.println("Would you like to double down?");
                    Scanner doubledown = new Scanner(System.in);
                    String doubled = doubledown.nextLine();
                    
                        System.out.println("Please enter yes or no.");
                        doubled = doubledown.nextLine();
                    
                    if(doubled.equals("yes"))
                    {
                        System.out.println("You have opted to double down!");
                    }
                
                System.out.println("Would you like to hit or stand?");
                Scanner hitorstand = new Scanner(System.in);
                String hitter = hitorstand.nextLine();
                while(!isHitorStand(hitter))
                {
                    System.out.println("Please enter 'hit' or 'stand'.");
                    hitter = hitorstand.nextLine();
                }
                while(hitter.equals("hit"))
                {
                    Hit(deck, hand);
                    System.out.println("Your hand is now:");
                    System.out.println(hand);
                    handvalue = calcHandValue(hand);
                    if(checkBust(handvalue))
                    {
                        Lose();
                        break;
                    }
                    System.out.println("Would you like to hit or stand?");
                    hitter = hitorstand.nextLine();
                }
                if(hitter.equals("stand"))
                {
                    int dealerhand = dealer.takeTurn(deck);
                    System.out.println("");
                    System.out.println("Here is the dealer's hand:");
                    dealer.showHand();
                    if(dealerhand>21)
                    {
                        Win();
                    }
                    else
                    {
                        int you = 21-handvalue;
                        int deal = 21-dealerhand;
                        if(you==deal)
                        {
                            Push();
                        }
                        if(you<deal)
                        {
                            Win();
                        }
                        if(deal<you)
                        {
                            Lose();
                        }
                    }
                }
            }
        System.out.println("Would you like to play again?");
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine();
        if(answer.equals("no"))
        {
            System.exit(0);
        }
    }
   
    public static boolean hasBlackJack(int handValue)
    {
        if(handValue==21)
        {
            return true;
        }
        return false;
    }
    
    public static int calcHandValue(ArrayList<Card> hand)
    {
        Card[] aHand = new Card[]{};
        aHand = hand.toArray(aHand);
        int handvalue=0;
        for(int i=0; i<aHand.length; i++)
        {
            handvalue += aHand[i].getValue();
            if(aHand[i].getValue()==11)
            {
                AceCounter++;
            }
            while(AceCounter>0 && handvalue>21)
            {
                handvalue-=10;
                AceCounter--;
            }
        }
        return handvalue;
    }
    
    public static void Win()
    {
        System.out.println("You won!");
    }
    
    public static void Lose()
    {
        System.out.println("You lost.");
    }
    
    public static void Push()
    {
        System.out.println("Push.");
    }
    
    public static void Hit(Deck deck, ArrayList<Card> hand)
    {
        hand.add(deck.drawCard());
        Card[] aHand = new Card[]{};
        aHand = hand.toArray(aHand);
        handvalue = 0;
        for(int i=0; i<aHand.length; i++)
        {
            handvalue += aHand[i].getValue();
            if(aHand[i].getValue()==11)
            {
                AceCounter++;
            }
            while(AceCounter>0 && handvalue>21)
            {
                handvalue-=10;
                AceCounter--;
            }
        }
    }
    
    public static boolean isHitorStand(String hitter)
    {
        if(hitter.equals("hit") || hitter.equals("stand"))
        {
            return true;
        }
        return false;
    }
    
    public static boolean checkBust(int handvalue)
    {
        if(handvalue>21)
        {
            System.out.println("Bust.");
            return true;
        }
        return false;
    }
}
