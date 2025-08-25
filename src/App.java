import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        // you went on a trip with 4 other friends (total 5 ppl)
        // each day everyone forgot to bring their wallet except ONE person
        // and that person paid for that day
        // scenario repeats for rest of trip
        // after 5 day trip, calculate the total amount of money
        // each person owe to each and every person
        
        List<String> friends = Arrays.asList("Alice", "Bob", "Carol", "Dave", "Eve");
        String[] payers = {"Alice", "Bob", "Bob", "Carol", "Alice"};
        double[] amounts = {150.0, 200.0, 180.0, 220.0, 250.0};

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

        Map<String, Double> balances = new HashMap<>();

        for(String friend: friends){
            balances.put(friend, 0.0);
        }

        System.out.println(balances);

        for(int day = 0; day < 5; day++){
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
    }
}
