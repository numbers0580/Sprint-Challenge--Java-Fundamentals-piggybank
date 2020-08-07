package local.peter.piggybank.controllers;

import local.peter.piggybank.models.Coin;
import local.peter.piggybank.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CoinController {
    @Autowired
    CoinRepository coinrepos;

    private List<Coin> findCoins(List<Coin> coinList, CheckCoin tester) {
        List<Coin> discretionary = new ArrayList<>();

        for(Coin change : coinList) {
            if(tester.test(change)) {
                discretionary.add(change);
            }
        }

        return discretionary;
    }

    //http://localhost:5280/total

    @GetMapping(value = "/total", produces = {"application/json"})
    public ResponseEntity<?> findTotals() {
        List<Coin> coinList = new ArrayList<>();
        coinrepos.findAll().iterator().forEachRemaining(coinList::add);

        /*  Desired Console Output:
            1 Quarter
            1 Dime
            5 Dollars
            3 Nickels
            7 Dimes
            1 Dollar
            10 Pennies
            The piggy bank holds 7.3
        */

        // I think the best way to achieve the above output is by using a for-loop
        double inTheBank = 0.0;

        for(Coin currentCoin : coinList) {
            //System.out.println(turtle.getCoinid() + ", " + turtle.getName() + ", " + turtle.getNameplural() + ", " + turtle.getValue() + ", " + turtle.getQuantity());
            if(currentCoin.getQuantity() == 1) {
                System.out.println(currentCoin.getQuantity() + " " + currentCoin.getName());
            } else {
                // Yes, this includes if there are 0 of the current coin we're checking.
                // Has anyone ever referred to 0 of something in the singular sense?
                System.out.println(currentCoin.getQuantity() + " " + currentCoin.getNameplural());
            }

            inTheBank += (currentCoin.getQuantity() * currentCoin.getValue());
        }

        System.out.println("The piggy bank holds $" + String.format("%.2f", inTheBank));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
