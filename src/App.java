import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // you went on a trip with 4 other friends (total 5 ppl)
        // each day everyone forgot to bring their wallet except ONE person
        // and that person paid for that day
        // scenario repeats for rest of trip
        // after 5 day trip, calculate the total amount of money
        // each person owe to each and every person
        
        // List<String> friends = Arrays.asList("Alice", "Bob", "Carol", "Dave", "Eve");

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of friends: ");
        int numFriends = sc.nextInt();
        sc.nextLine();

        List<String> friends = new ArrayList<>(); // empty arrayList (dynamic)
        for(int i = 0; i < numFriends; i++){
            System.out.print("Enter friend " + (i + 1) + " name: ");
            friends.add(sc.nextLine().trim());
        }

        System.out.print("Enter number of expense records: ");
        int numRecords = sc.nextInt();  // number of purchase entries (days)
        sc.nextLine();

        String[] payers = new String[numRecords];
        double[] amounts = new double[numRecords];

        for(int i = 0; i < numRecords; i++){
            System.out.print("Enter name of purchaser from day "+(i+1)+": ");
            payers[i] = sc.nextLine();
            
            System.out.print("Enter amount purchased from day " + (i+1) + ": ");
            amounts[i] = sc.nextDouble();
            sc.nextLine();
        }

        // everyone spends equal amounts
        // day 1
        // alice +120
        // bob -30
        // carol -30
        // dave -30
        // eve -30

        //day 2
        // alice +80   (-40)
        // bob +130      (+160)
        // carol -70    (-40)
        // dave -70     (-40)
        // eve -70      (-40)

        HashMap<String, Double> balances = new HashMap<>();

        for(String friend: friends){
            balances.put(friend, 0.0);
        }

        System.out.println(balances);

        for(int day = 0; day < numRecords; day++){
            // retrieve the total and payer of EACH day
            String payer = payers[day];
            double total = amounts[day];
            double share = total/friends.size();

            for(String friend:friends){
                if(friend.equals(payer)){
                    balances.put(friend, balances.get(friend) + total-share);

                }
                else{
                    balances.put(friend, balances.get(friend) - share);
                }
            }
        }
        System.out.println("Net balances: ");
        for(String friend:friends){
            System.out.println(friend + " " + balances.get(friend));
        }

        System.out.println("Simplified debts");
        simplifyDebts(balances);

        // simplify the expenses
        // end goal: the payers get their money back
        // creditors: 
        // debtors:
        // find the largest creditor and the smallest debtor and pay out
        // alice - dave --> alice: 0, dave: 0
        // Alice 0.0
        // Bob 180.0
        // Carol 20.0
        // Dave 0.0
        // Eve -200.0

        // bob - eve --> bob: 0, eve: -20

        // Alice 0.0
        // Bob 0.0
        // Carol 20.0
        // Dave 0.0
        // Eve -20.0

    }

    static void simplifyDebts(HashMap <String, Double> balances){
        // finds largest creditor and smallest debtor
        // when  b.amount is first in (a,b) u r looking for the larger amount
        PriorityQueue <Person> creditors = new PriorityQueue<>((a,b) -> Double.compare(b.amount, a.amount));
        PriorityQueue<Person> debtors = new PriorityQueue<>((a,b) -> Double.compare(a.amount, b.amount));

        // entry -> each pair (key/value pair)
        // offer -> adds into priority queue

        // same thing as for(String name: names)
        for(HashMap.Entry<String, Double> entry: balances.entrySet()){
            String name = entry.getKey();
            double amount = entry.getValue();
            if(amount > 0){
                creditors.offer(new Person(name, amount));
            }

            else if(amount < 0){
                debtors.offer(new Person(name, -amount));
            }
        }
        while(!creditors.isEmpty() && !debtors.isEmpty()){
            Person creditor = creditors.poll();
            Person debtor = debtors.poll();

            double min = Math.min(creditor.amount, debtor.amount);
            System.out.printf("%s owes %s: $%.2f\n", debtor.name, creditor.name, min);

            // so if zero then u won't be readded into the hashmap
            if(creditor.amount > min){
                creditors.offer(new Person(creditor.name, creditor.amount - min));
            }
            if(debtor.amount > min){
                debtors.offer(new Person(debtor.name, debtor.amount - min));
            }
        }
            
    }

    // need new type bc it has to hold both string and double
    static class Person{
        String name;
        double amount;

        Person(String name, double amount){
            this.name = name;
            this.amount = amount;
        }
    }
}
