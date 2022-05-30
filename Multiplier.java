import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Multiplier implements ActionListener
{
    final int min = 1;
    final int max = 200;
    int x = 0;
    int initCredit = 10000;
    int betAmount = 0;
    JFrame frame = new JFrame();
    JLabel betAmt = new JLabel("Bet Amount:");
    JTextField bet = new JTextField();
    JButton startButton = new JButton("Start");
    JButton playButton = new JButton("Play Again");
    JLabel multiplierLabel = new JLabel();
    JLabel outcome = new JLabel();
    JTextField wallet = new JTextField("Credits: " + initCredit);
    JTextField output = new JTextField(); 
    int elapsedTime = 0;
    int deciMultiplier = 0;
    int intMultiplier = 1;
    boolean started = false;
    int earnings = 0;
    String deci_String = String.format("%02d", deciMultiplier);
    String int_String = Integer.toString(intMultiplier);
    Timer timer = new Timer(1, new ActionListener() 
    {
        public void actionPerformed(ActionEvent e)
        {
            elapsedTime = elapsedTime + 1;
            /*intMultiplier = (int)Math.floor(0.99 + (Math.exp(elapsedTime/1000)*0.01));
            deciMultiplier = ((int)Math.floor(Math.round((0.99 + (Math.exp(elapsedTime/1000)*0.01))*100.0)))%100;*/
            intMultiplier = (1 + elapsedTime/1000);
            deciMultiplier = ((elapsedTime/10)%100);
            deci_String = String.format("%02d", deciMultiplier);
            int_String = Integer.toString(intMultiplier);
            multiplierLabel.setText(int_String + "." + deci_String + "x");
            x = (int)Math.floor(Math.random()*(max-min+1)+min);
            if (x == 1)
            {
                timer.stop();
                multiplierLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
                multiplierLabel.setText("Crashed @ " + int_String + "." + deci_String + "x");
                started = false;
                startButton.setText("Start");
                startButton.setEnabled(false);
                playButton.setEnabled(true);
            }
        }
    }
    );
    
    public Multiplier()
    {
        multiplierLabel.setText(int_String + "." + deci_String + "x");
        multiplierLabel.setBounds(25,50,200,200);
        multiplierLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
        multiplierLabel.setBorder(BorderFactory.createBevelBorder(1));
        multiplierLabel.setOpaque(true);
        multiplierLabel.setHorizontalAlignment(JTextField.CENTER);
        
        startButton.setBounds(225,150,150,50);
        startButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        startButton.setFocusable(false);
        startButton.addActionListener(this);
        
        
        playButton.setBounds(225,200,150,50);
        playButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        playButton.setFocusable(false);
        playButton.addActionListener(this);
        
        betAmt.setBounds(225,50,75,25);
        betAmt.setFont(new Font("Verdana", Font.PLAIN, 10));
        betAmt.setFocusable(false);
        
        bet.setBounds(225,75,150,25);
        bet.setFont(new Font("Verdana", Font.PLAIN, 10));
        bet.setFocusable(true);
        
        wallet.setBounds(25,250,150,50);
        wallet.setFont(new Font("Verdana", Font.PLAIN, 15));
        wallet.setFocusable(false);
        
        output.setBounds(175,250,200,50);
        output.setFont(new Font("Verdana", Font.PLAIN, 10));
        output.setFocusable(false);
        
        outcome.setBounds(225,50,75,25);
        outcome.setFont(new Font("Verdana", Font.PLAIN, 10));
        outcome.setFocusable(false);

        frame.add(startButton);
        frame.add(playButton);
        frame.add(multiplierLabel);
        frame.add(betAmt);
        frame.add(bet);
        frame.add(wallet);
        frame.add(output);
        
        bet.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String input = bet.getText();
                try{
                    initCredit = initCredit - Integer.parseInt(input);
                    bet.setEnabled(false);
                    betAmount = Integer.parseInt(input);
                    startButton.setEnabled(true);
                    output.setText("");
                    if (initCredit < 0)
                    {
                        bet.setEnabled(true);
                        output.setText("Error: Not enough credits.");
                        initCredit = initCredit + Integer.parseInt(input);
                        startButton.setEnabled(false);
                        betAmount = 0;
                    }
                    if (Integer.parseInt(input) < 100)
                    {
                        bet.setEnabled(true);
                        output.setText("Minimum bet is 100");
                        initCredit = initCredit + Integer.parseInt(input);
                        startButton.setEnabled(false);
                        betAmount = 0;
                    }
                    wallet.setText("Credits: " + initCredit);
                }
                catch (NumberFormatException ex)
                {
                    output.setText("Error: Not a valid number.");
                }
                
            }
        }
        );
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        frame.setLayout(null);
        frame.setVisible(true);
        playButton.setEnabled(false);
        startButton.setEnabled(false);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == startButton) 
        {
            if (started == false)
            {
                started = true;
                startButton.setText("Cashout");
                start();
            }
            else
            {
                started = false;
                startButton.setText("Start");
                bet.setEnabled(true);
                startButton.setEnabled(false);
                earnings = (intMultiplier * betAmount) + (deciMultiplier * betAmount / 100);
                output.setText("You've won " + earnings + "!");
                playButton.setEnabled(true);
                bet.setEnabled(false);
                cashOut();
            }
        }
        if (e.getSource() == playButton)
        {
            started = false;
            startButton.setText("Start");
            bet.setEnabled(true);
            playButton.setEnabled(false);
            reset();
        }
    }
    
    public void start()
    {
        timer.start();
    }
    
    public void cashOut()
    {
        timer.stop();
    }
    
    public void reset()
    {
        timer.stop();
        elapsedTime=0;
        elapsedTime = 0;
        deciMultiplier = 0;
        intMultiplier = 1;
        deci_String = String.format("%02d", deciMultiplier);
        int_String = Integer.toString(intMultiplier);
        multiplierLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
        multiplierLabel.setText(int_String + "." + deci_String + "x");
        output.setText(earnings + " credits have been added.");
        initCredit = initCredit + earnings;
        wallet.setText("Credits: " + (initCredit));
        earnings = 0;
    }
}
