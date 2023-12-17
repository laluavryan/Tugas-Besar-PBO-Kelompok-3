package batuguntingkertas;

import java.util.*;
import javax.swing.*;

import batuguntingkertas.Menu;

import java.awt.*;
import java.awt.event.*;

public class Menu extends JPanel {

	private static final long serialVersionUID = 1L;
	final int WIDTH = 850;
	final int HEIGHT = (int) (WIDTH * 0.5555);

	JButton play = new JButton();
	JButton leaderboard = new JButton();
	JButton about = new JButton();
	JButton exit = new JButton();
	JPanel buttonContainer = new JPanel();

	JLabel judulLabel = new JLabel("ROCK PAPER SCISSOR");
	JPanel judulContainer = new JPanel();

	Menu() {
		this.setBackground(Color.decode("#d5edff"));
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				add(new GamePanel());
				revalidate();
				repaint();
			}
		});

		leaderboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException evt) {
						evt.printStackTrace();
					}
					Leaderboard leaderboardFrame = new Leaderboard();
					leaderboardFrame.setVisible(true);
				});
				revalidate();
				repaint();
			}
		});

		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();

				ImageIcon playerIcon = new ImageIcon("\\Koding\\Java\\ProjekPBO\\src\\\\asset\\player.png");
		        Image scaledPlayer= playerIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		        ImageIcon scaledIconPlayer = new ImageIcon(scaledPlayer);
				
		        ImageIcon botIcon = new ImageIcon("\\Koding\\Java\\ProjekPBO\\src\\\\asset\\bot.png");
		        Image scaledBot= botIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		        ImageIcon scaledIconBot= new ImageIcon(scaledBot);
		        
				JPanel aboutPanel = new JPanel();
				aboutPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
				aboutPanel.setBackground(Color.decode("#d5edff"));
				
				JLabel playerLabel = new JLabel(scaledIconPlayer);
				JLabel botLabel = new JLabel(scaledIconBot);
				
				JButton backButton = new JButton();
				backButton.setFont(new Font("Nirmala UI", 1, 14));
				backButton.setPreferredSize(new Dimension(75, 20));
				backButton.setBackground(Color.decode("#3465ff"));
				backButton.setForeground(Color.WHITE);
				backButton.setText("BACK");
				backButton.setFocusable(false);
				JPanel exitContainer = new JPanel();
				exitContainer.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
				exitContainer.setPreferredSize(new Dimension(WIDTH - 10, 20));
				exitContainer.setOpaque(false);
				exitContainer.add(backButton);
				
				backButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						removeAll();
						add(new Menu());
						revalidate();
						repaint();
					}
				});
				
				JLabel playerAbout1 = new JLabel("SKILL 1 : Can heal it self with 50% of their HP");
				playerAbout1.setHorizontalAlignment(SwingConstants.CENTER);
				playerAbout1.setPreferredSize(new Dimension(400,50));
				playerAbout1.setFont(new Font("Nirmala UI", 1, 14));
				
				JLabel playerAbout2 = new JLabel("<html>SKILL 2 : Double the damage and "
						+ "ignore the opponent's defense<html>");
				playerAbout2.setHorizontalAlignment(SwingConstants.CENTER);
				playerAbout2.setPreferredSize(new Dimension(400,50));
				playerAbout2.setFont(new Font("Nirmala UI", 1, 14));
				
				JLabel botAbout1 = new JLabel("SKILL 1 : Can heal it self with 70% of their HP");
				botAbout1.setHorizontalAlignment(SwingConstants.CENTER);
				botAbout1.setPreferredSize(new Dimension(400,50));
				botAbout1.setFont(new Font("Nirmala UI", 1, 14));
				
				JLabel botAbout2 = new JLabel("<html>SKILL 2 : 50% the damage "
						+ "and ignore the opponent's defense<html>");
				botAbout2.setHorizontalAlignment(SwingConstants.CENTER);
				botAbout2.setPreferredSize(new Dimension(400,50));
				botAbout2.setFont(new Font("Nirmala UI", 1, 14));
				
				JPanel imageContainer = new JPanel();
				JPanel center = new JPanel();
				center.setPreferredSize(new Dimension(50,0));
				imageContainer.setLayout(new BorderLayout());
				imageContainer.setOpaque(false);
				imageContainer.add(playerLabel, BorderLayout.WEST);
				imageContainer.add(center, BorderLayout.CENTER);
				imageContainer.add(botLabel, BorderLayout.EAST);
				
				aboutPanel.add(exitContainer);
				aboutPanel.add(imageContainer);
				aboutPanel.add(playerAbout1);
				aboutPanel.add(botAbout1);
				aboutPanel.add(playerAbout2);
				aboutPanel.add(botAbout2);
				
				
				add(aboutPanel);
				
				revalidate();
				repaint();
			}
		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		buttonContainer.setPreferredSize(new Dimension(500, 350));
		buttonContainer.setBackground(Color.decode("#5be6ff"));
		buttonContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
		judulContainer.setPreferredSize(new Dimension(500, 100));
		judulContainer.setBackground(Color.red);
		judulContainer.setOpaque(true);

		play.setFont(new Font("Nirmala UI", 1, 40));
		play.setPreferredSize(new Dimension(400, 50));
		play.setBackground(Color.decode("#3465ff"));
		play.setForeground(Color.WHITE);
		play.setText("PLAY");
		play.setFocusable(false);

		leaderboard.setFont(new Font("Nirmala UI", 1, 40));
		leaderboard.setPreferredSize(new Dimension(400, 50));
		leaderboard.setBackground(Color.decode("#3465ff"));
		leaderboard.setForeground(Color.WHITE);
		leaderboard.setText("LEADERBOARD");
		leaderboard.setFocusable(false);

		about.setFont(new Font("Nirmala UI", 1, 40));
		about.setPreferredSize(new Dimension(400, 50));
		about.setBackground(Color.decode("#3465ff"));
		about.setForeground(Color.WHITE);
		about.setText("ABOUT");
		about.setFocusable(false);

		exit.setFont(new Font("Nirmala UI", 1, 40));
		exit.setPreferredSize(new Dimension(400, 50));
		exit.setBackground(Color.decode("#3465ff"));
		exit.setForeground(Color.WHITE);
		exit.setText("EXIT");
		exit.setFocusable(false);

		buttonContainer.add(play);
		buttonContainer.add(leaderboard);
		buttonContainer.add(about);
		buttonContainer.add(exit);

		judulLabel.setHorizontalAlignment(SwingConstants.CENTER);
		judulLabel.setFont(new Font("Nirmala UI", 1, 35));
		judulLabel.setPreferredSize(new Dimension(400, 90));
		judulLabel.setBackground(Color.decode("#3465ff"));
		judulLabel.setForeground(Color.WHITE);
		judulLabel.setOpaque(false);

		judulContainer.add(judulLabel);
		this.add(judulContainer);
		this.add(buttonContainer);

	}
}
