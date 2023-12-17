package batuguntingkertas;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

import API.Database;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 7841699317463796446L;
	final int WIDTH = 850;
	final int HEIGHT = (int) (WIDTH * 0.5555);
	JPanel nameContainer = new JPanel();
	JLabel youLabel = new JLabel();
	JLabel computerLabel = new JLabel();

	JPanel panelHealth = new JPanel();
	TimerCustome timerLabel = new TimerCustome(5);
	JProgressBar playerHealthBar = new JProgressBar();
	JProgressBar botHealthBar = new JProgressBar();

	JPanel panelEnergy = new JPanel();
	JLabel scoreLabel = new JLabel();
	JProgressBar playerEnergyBar = new JProgressBar();
	JProgressBar botEnergyBar = new JProgressBar();

	JButton batuButton = new JButton();
	JButton kertasButton = new JButton();
	JButton guntingButton = new JButton();
	JButton exitButton = new JButton();
	JButton backButton = new JButton();
	JToggleButton skill1Button = new JToggleButton();
	JToggleButton skill2Button = new JToggleButton();

	Image kertas = new ImageIcon("\\Koding\\Java\\ProjekPBO\\src\\asset\\kertas.png").getImage();
	Image batu = new ImageIcon("\\Koding\\Java\\ProjekPBO\\src\\asset\\batu.png").getImage();
	Image gunting = new ImageIcon("\\Koding\\Java\\ProjekPBO\\src\\asset\\gunting.png").getImage();
	Image mati = new ImageIcon("\\Koding\\Java\\ProjekPBO\\src\\asset\\mati.png").getImage();

	PlayerArea playerArea = new PlayerArea();
	BotArea botArea = new BotArea();

	Bot bot = new Bot("YOU", 1000, 100, 200, 30);
	User user = new User("BOT", 1000, 100, 200, 30);

	Thread playerMovement;
	Thread botMovement;

	int score = 0;
	boolean startPlay = false;

	GamePanel() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.decode("#d5edff"));
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

		youLabel.setPreferredSize(new Dimension(330, 0));
		youLabel.setText("YOU");
		youLabel.setForeground(Color.decode("#10329f"));
		youLabel.setHorizontalAlignment(SwingConstants.CENTER);
		youLabel.setFont(new Font("Nirmala UI", 1, 20));
		computerLabel.setPreferredSize(new Dimension(330, 0));
		computerLabel.setText("BOT");
		computerLabel.setForeground(Color.decode("#10329f"));
		computerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		computerLabel.setFont(new Font("Nirmala UI", 1, 20));

		nameContainer.setOpaque(false);
		nameContainer.setLayout(new BorderLayout());
		nameContainer.setPreferredSize(new Dimension(WIDTH - 10, 25));
		nameContainer.add(youLabel, BorderLayout.WEST);
		nameContainer.add(computerLabel, BorderLayout.EAST);

		panelHealth.setPreferredSize(new Dimension(WIDTH - 10, 40));
		panelHealth.setOpaque(false);
		panelHealth.setLayout(new BorderLayout());

		playerHealthBar.setBackground(Color.decode("#FF3300")); // red
		botHealthBar.setBackground(Color.decode("#FF3300")); // red
		playerHealthBar.setForeground(Color.decode("#66FF66")); // green
		botHealthBar.setForeground(Color.decode("#66FF66")); // green
		playerHealthBar.setPreferredSize(new Dimension(330, 0));
		botHealthBar.setPreferredSize(new Dimension(330, 0));
		playerHealthBar.setStringPainted(true);
		botHealthBar.setStringPainted(true);
		playerHealthBar.setBorderPainted(false);
		botHealthBar.setBorderPainted(false);

		playerHealthBar.setMinimum(0);
		botHealthBar.setMinimum(0);
		playerHealthBar.setMaximum(1000);
		botHealthBar.setMaximum(1000);
		playerHealthBar.setValue(user.getHealth());
		botHealthBar.setValue(bot.getHealth());
		playerHealthBar.setFont(new Font("Nirmala UI", 1, 18));
		botHealthBar.setFont(new Font("Nirmala UI", 1, 18));
		playerHealthBar.setString(playerHealthBar.getValue() + "/" + playerHealthBar.getValue());
		botHealthBar.setString(botHealthBar.getValue() + "/" + botHealthBar.getValue());

		playerHealthBar.setUI(new BasicProgressBarUI() {
			protected Color getSelectionBackground() {
				return Color.decode("#66FF66"); // Warna teks pada progress bar
			}

			protected Color getSelectionForeground() {
				return Color.decode("#FF3300"); // Warna teks pada progress bar
			}
		});
		botHealthBar.setUI(new BasicProgressBarUI() {
			protected Color getSelectionBackground() {
				return Color.decode("#66FF66"); // Warna teks pada progress bar
			}

			protected Color getSelectionForeground() {
				return Color.decode("#FF3300"); // Warna teks pada progress bar
			}
		});

		panelHealth.add(timerLabel.getLabel(), BorderLayout.CENTER);
		panelHealth.add(playerHealthBar, BorderLayout.WEST);
		panelHealth.add(botHealthBar, BorderLayout.EAST);

		////////////////////////////

		panelEnergy.setPreferredSize(new Dimension(WIDTH - 10, 25));
		panelEnergy.setOpaque(false);
		panelEnergy.setLayout(new BorderLayout());

		playerEnergyBar.setBackground(Color.decode("#2205ff")); // red
		botEnergyBar.setBackground(Color.decode("#2205ff")); // red
		playerEnergyBar.setForeground(Color.decode("#5bf1ff")); // green
		botEnergyBar.setForeground(Color.decode("#5bf1ff")); // green
		playerEnergyBar.setPreferredSize(new Dimension(330, 0));
		botEnergyBar.setPreferredSize(new Dimension(330, 0));
		playerEnergyBar.setStringPainted(true);
		botEnergyBar.setStringPainted(true);
		playerEnergyBar.setBorderPainted(false);
		botEnergyBar.setBorderPainted(false);

		playerEnergyBar.setMinimum(0);
		botEnergyBar.setMinimum(0);
		playerEnergyBar.setMaximum(200);
		botEnergyBar.setMaximum(200);
		playerEnergyBar.setValue(user.getEnergy());
		botEnergyBar.setValue(bot.getEnergy());
		playerEnergyBar.setFont(new Font("Nirmala UI", 1, 12));
		botEnergyBar.setFont(new Font("Nirmala UI", 1, 12));
		playerEnergyBar.setString(playerEnergyBar.getValue() + "/" + playerEnergyBar.getValue());
		botEnergyBar.setString(botEnergyBar.getValue() + "/" + botEnergyBar.getValue());

		playerEnergyBar.setUI(new BasicProgressBarUI() {
			protected Color getSelectionBackground() {
				return Color.decode("#5bf1ff"); // Warna teks pada progress bar
			}

			protected Color getSelectionForeground() {
				return Color.decode("#2205ff"); // Warna teks pada progress bar
			}
		});
		botEnergyBar.setUI(new BasicProgressBarUI() {
			protected Color getSelectionBackground() {
				return Color.decode("#5bf1ff"); // Warna teks pada progress bar
			}

			protected Color getSelectionForeground() {
				return Color.decode("#2205ff"); // Warna teks pada progress bar
			}
		});

		scoreLabel.setFont(new Font("Nirmala UI", 1, 12));
		scoreLabel.setOpaque(false);
		scoreLabel.setForeground(Color.decode("#10329f"));
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setText("SCORE : 0");

		panelEnergy.add(scoreLabel, BorderLayout.CENTER);
		panelEnergy.add(playerEnergyBar, BorderLayout.WEST);
		panelEnergy.add(botEnergyBar, BorderLayout.EAST);

		/////////////////////////////

		playerMovement = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					playerArea.move();
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		});
		botMovement = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					botArea.move();
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		});

		batuButton.setFont(new Font("Nirmala UI", 1, 18));
		batuButton.setPreferredSize(new Dimension(150, 35));
		batuButton.setBackground(Color.decode("#3465ff"));
		batuButton.setForeground(Color.WHITE);
		batuButton.setText("ROCK");
		batuButton.setFocusable(false);

		guntingButton.setFont(new Font("Nirmala UI", 1, 18));
		guntingButton.setPreferredSize(new Dimension(150, 35));
		guntingButton.setBackground(Color.decode("#3465ff"));
		guntingButton.setForeground(Color.WHITE);
		guntingButton.setText("SCISSOR");
		guntingButton.setFocusable(false);

		kertasButton.setFont(new Font("Nirmala UI", 1, 18));
		kertasButton.setPreferredSize(new Dimension(150, 35));
		kertasButton.setBackground(Color.decode("#3465ff"));
		kertasButton.setForeground(Color.WHITE);
		kertasButton.setText("PAPER");
		kertasButton.setFocusable(false);

		skill1Button.setFont(new Font("Nirmala UI", 1, 18));
		skill1Button.setPreferredSize(new Dimension(150, 35));
		skill1Button.setBackground(Color.decode("#890677"));
		skill1Button.setForeground(Color.WHITE);
		skill1Button.setText("HEAL");
		skill1Button.setFocusable(false);

		skill2Button.setFont(new Font("Nirmala UI", 1, 18));
		skill2Button.setPreferredSize(new Dimension(150, 35));
		skill2Button.setBackground(Color.decode("#890677"));
		skill2Button.setForeground(Color.WHITE);
		skill2Button.setText("CRITICAL");
		skill2Button.setFocusable(false);

		batuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timerLabel.hasExpired()) {
					timerLabel.restart(5);
					botAttack(10);
					repaint();
					revalidate();
				} else {
					timerLabel.restart(5);
					playerArea.setImage(batu);
					user.setSign("batu");
					botPick();
					startPlay();
					checkWin();
					repaint();
					revalidate();
				}

			}
		});
		guntingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timerLabel.hasExpired()) {
					timerLabel.restart(5);
					botAttack(10);
					repaint();
					revalidate();
				} else {
					timerLabel.restart(5);
					playerArea.setImage(gunting);
					user.setSign("gunting");
					botPick();
					startPlay();
					checkWin();
					repaint();
					revalidate();
				}

			}
		});
		kertasButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timerLabel.hasExpired()) {
					timerLabel.restart(5);
					botAttack(10);
					repaint();
					revalidate();
				} else {
					timerLabel.restart(5);
					playerArea.setImage(kertas);
					user.setSign("kertas");
					botPick();
					startPlay();
					checkWin();
					repaint();
					revalidate();
				}

			}
		});

		skill1Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (skill1Button.isSelected() == true) {
					skill2Button.setSelected(false);
				}
			}
		});
		skill2Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (skill2Button.isSelected() == true) {
					skill1Button.setSelected(false);
				}
			}
		});
		exitButton.setFont(new Font("Nirmala UI", 1, 14));
		exitButton.setPreferredSize(new Dimension(75, 20));
		exitButton.setBackground(Color.decode("#3465ff"));
		exitButton.setForeground(Color.WHITE);
		exitButton.setText("EXIT");
		exitButton.setFocusable(false);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION);
				if (confirmed == JOptionPane.YES_OPTION) {
					playerMovement.interrupt();
					botMovement.interrupt();
					System.exit(0);
				}
			}
		});

		backButton.setFont(new Font("Nirmala UI", 1, 14));
		backButton.setPreferredSize(new Dimension(75, 20));
		backButton.setBackground(Color.decode("#3465ff"));
		backButton.setForeground(Color.WHITE);
		backButton.setText("BACK");
		backButton.setFocusable(false);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				botMovement.interrupt();
				playerMovement.interrupt();
				timerLabel.stopTimer();
				removeAll();
				add(new Menu());
				revalidate();
				repaint();
			}
		});

		JPanel exitContainer = new JPanel();
		exitContainer.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		exitContainer.setPreferredSize(new Dimension(WIDTH - 10, 20));
		exitContainer.setOpaque(false);
		exitContainer.add(exitButton);
		exitContainer.add(backButton);

		timerLabel.start();
		this.add(exitContainer);
		this.add(nameContainer);
		this.add(panelHealth);
		this.add(panelEnergy);
		this.add(playerArea);
		this.add(botArea);
		this.add(batuButton);
		this.add(guntingButton);
		this.add(kertasButton);
		this.add(skill1Button);
		this.add(skill2Button);
	}

	void checkWin() {
		if (timerLabel.hasExpired()) {
			botAttack(10);
		} else if (user.getSign().equals(bot.getSign())) {
			System.out.println("DRAW");
		} else if ((user.getSign().equals("batu") && bot.getSign().equals("gunting"))
				|| (user.getSign().equals("gunting") && bot.getSign().equals("kertas"))
				|| (user.getSign().equals("kertas") && bot.getSign().equals("batu"))) {

			if (user.getEnergy() >= 200) {
				if (skill1Button.isSelected() == true) { // HEAL
					score += 200;
					scoreLabel.setText("SCORE : " + String.valueOf(score));
					scoreLabel.repaint();

					user.skill1();
					playerHealthBar.setValue(user.getHealth());
					playerHealthBar.setString(playerHealthBar.getValue() + "/1000");

					user.setEnergy(0);
					playerEnergyBar.setValue(user.getEnergy());
					playerEnergyBar.setString(playerEnergyBar.getValue() + "/200");
					skill1Button.setSelected(false);
				} else if (skill2Button.isSelected() == true) { // CRITICAL
					score += 200;
					scoreLabel.setText("SCORE : " + String.valueOf(score));
					scoreLabel.repaint();

					userAttack(user.skill2());

					user.setEnergy(0);
					playerEnergyBar.setValue(user.getEnergy());
					playerEnergyBar.setString(playerEnergyBar.getValue() + "/200");
					skill2Button.setSelected(false);
				} else {
					score += 50;
					scoreLabel.setText("SCORE : " + String.valueOf(score));
					scoreLabel.repaint();

					userAttack(user.getAttack() - bot.getDefence());

					bot.setEnergy(bot.getEnergy() + 25);
					botEnergyBar.setValue(bot.getEnergy());
					botEnergyBar.setString(botEnergyBar.getValue() + "/200");

					user.setEnergy(user.getEnergy() + 10);
					playerEnergyBar.setValue(user.getEnergy());
					playerEnergyBar.setString(playerEnergyBar.getValue() + "/200");
				}
			} else {
				score += 50;
				scoreLabel.setText("SCORE : " + String.valueOf(score));
				scoreLabel.repaint();

				userAttack(user.getAttack() - bot.getDefence());

				bot.setEnergy(bot.getEnergy() + 25);
				botEnergyBar.setValue(bot.getEnergy());
				botEnergyBar.setString(botEnergyBar.getValue() + "/200");

				user.setEnergy(user.getEnergy() + 10);
				playerEnergyBar.setValue(user.getEnergy());
				playerEnergyBar.setString(playerEnergyBar.getValue() + "/200");
			}

		} else if ((bot.getSign().equals("batu") && user.getSign().equals("gunting"))
				|| (bot.getSign().equals("gunting") && user.getSign().equals("kertas"))
				|| (bot.getSign().equals("kertas") && user.getSign().equals("batu"))) {

			if (bot.getEnergy() >= 200) {
				Random random = new Random();
				int pick = random.nextInt(3);
				if (pick == 0) {
					score -= 50;
					if (score <= 0) {
						score = 0;
					}
					scoreLabel.setText("SCORE : " + String.valueOf(score));
					scoreLabel.repaint();

					bot.skill1();
					botHealthBar.setValue(bot.getHealth());
					botHealthBar.setString(botHealthBar.getValue() + "/1000");

					bot.setEnergy(0);
					botEnergyBar.setValue(bot.getEnergy());
					botEnergyBar.setString(botEnergyBar.getValue() + "/200");
				} else if (pick == 1) {
					score -= 50;
					if (score <= 0) {
						score = 0;
					}
					scoreLabel.setText("SCORE : " + String.valueOf(score));
					scoreLabel.repaint();

					botAttack(bot.skill2());

					bot.setEnergy(0);
					botEnergyBar.setValue(bot.getEnergy());
					botEnergyBar.setString(botEnergyBar.getValue() + "/200");
				} else if (pick == 2) {
					score -= 10;
					if (score <= 0) {
						score = 0;
					}
					scoreLabel.setText("SCORE : " + String.valueOf(score));
					scoreLabel.repaint();

					botAttack(bot.getAttack() - user.getDefence());
				}
			} else {
				score -= 10;
				if (score <= 0) {
					score = 0;
				}
				scoreLabel.setText("SCORE : " + String.valueOf(score));
				scoreLabel.repaint();

				botAttack(bot.getAttack() - user.getDefence());

				bot.setEnergy(bot.getEnergy() + 10);
				botEnergyBar.setValue(bot.getEnergy());
				botEnergyBar.setString(botEnergyBar.getValue() + "/200");

				user.setEnergy(user.getEnergy() + 25);
				playerEnergyBar.setValue(user.getEnergy());
				playerEnergyBar.setString(playerEnergyBar.getValue() + "/200");
			}
		}
	}

	void userAttack(int damage) {
		Thread attack = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < damage; i++) {
					bot.setHealth(bot.getHealth() - 1);
					botHealthBar.setValue(bot.getHealth());
					botHealthBar.setString(botHealthBar.getValue() + "/1000");
					try {
						Thread.sleep(5);
					} catch (Exception e) {

					}
				}
				if (bot.dead()) {
					updateScore(user.getName(), 940);

					timerLabel.stopTimer();
					botArea.setDead(mati);
					botMovement.interrupt();
					repaint();

					batuButton.setEnabled(false);
					guntingButton.setEnabled(false);
					kertasButton.setEnabled(false);

					JFrame inputFrame = new JFrame();
					JTextField textInput = new JTextField();
					JButton done = new JButton("DONE");

					inputFrame.setSize(370, 150);
					inputFrame.setLocationRelativeTo(null);
					textInput.setPreferredSize(new Dimension(300,25));
					
					done.addActionListener(e -> {
						// Ambil teks dari text field jika diperlukan
						String inputText = textInput.getText();
						// Lakukan apa yang diperlukan dengan inputText
						updateScore(inputText, score);
						// Tutup frame input
						inputFrame.dispose();
						botMovement.interrupt();
						playerMovement.interrupt();
						timerLabel.stopTimer();
						removeAll();
						add(new Menu());
						revalidate();
						repaint();
					});

					JPanel panel = new JPanel();
					panel.setLayout(new FlowLayout());
					
					JLabel title= new JLabel("ENTER NAME!");
					title.setHorizontalAlignment(SwingConstants.CENTER);
					title.setFont(new Font("Nirmala UI", 1, 14));
					title.setOpaque(false);
					title.setForeground(Color.BLACK);
					title.setPreferredSize(new Dimension(100,25));
					
					panel.add(title);
					panel.add(textInput);
					panel.add(done);

					inputFrame.add(panel);
					inputFrame.setVisible(true);

				}
			}
		});
		attack.start();
	}

	void botAttack(int damage) {
		Thread attack = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < damage; i++) {
					user.setHealth(user.getHealth() - 1);
					playerHealthBar.setValue(user.getHealth());
					playerHealthBar.setString(playerHealthBar.getValue() + "/1000");
					try {
						Thread.sleep(5);
					} catch (Exception e) {

					}
				}
				if (user.dead()) {
					timerLabel.stopTimer();
					playerArea.setDead(mati);
					playerMovement.interrupt();
					repaint();

					batuButton.setEnabled(false);
					guntingButton.setEnabled(false);
					kertasButton.setEnabled(false);

					JOptionPane.showConfirmDialog(null, "YOU LOSE!", "LOSING", JOptionPane.YES_OPTION);
					
					botMovement.interrupt();
					playerMovement.interrupt();
					timerLabel.stopTimer();
					removeAll();
					add(new Menu());
					revalidate();
					repaint();
				}
			}
		});
		attack.start();
	}

	void botPick() {
		ArrayList<Image> imageBot = new ArrayList<>();
		imageBot.add(kertas);
		imageBot.add(batu);
		imageBot.add(gunting);
		Random random = new Random();
		int randompick = random.nextInt(3);
		if (randompick == 0) {
			bot.setSign("kertas");
		} else if (randompick == 1) {
			bot.setSign("batu");
		} else if (randompick == 2) {
			bot.setSign("gunting");
		}
		botArea.setImage(imageBot.get(randompick));
	}

	void startPlay() {
		if (!isPlay()) {
			playerMovement.start();
			botMovement.start();
			setPlay(); // Pembaruan nilai startPlay setelah memulai permainan
		}
	}

	boolean isPlay() {
		return startPlay;
	}

	void setPlay() {
		startPlay = true;
	}

	void updateScore(String nama, int score) {
		String selectQuery = "SELECT id_user FROM user WHERE nama = ?";
		String updateQuery = "UPDATE user SET score = ? WHERE nama = ?";
		String insertQuery = "INSERT INTO user (nama, score) VALUES (?, ?)";

		try {
			Connection connection = Database.getConnection();

			// Mengecek apakah nama sudah ada di tabel user
			PreparedStatement selectPst = connection.prepareStatement(selectQuery);
			selectPst.setString(1, nama);
			ResultSet rs = selectPst.executeQuery();

			if (rs.next()) {
				// Jika nama sudah ada, update nilai score
				PreparedStatement updatePst = connection.prepareStatement(updateQuery);
				updatePst.setInt(1, score);
				updatePst.setString(2, nama);
				updatePst.executeUpdate();
				updatePst.close();
				System.out.println("Score updated for " + nama);
			} else {
				// Jika nama belum ada, lakukan penambahan data baru
				PreparedStatement insertPst = connection.prepareStatement(insertQuery);
				insertPst.setString(1, nama);
				insertPst.setInt(2, score);
				insertPst.executeUpdate();
				insertPst.close();
				System.out.println("New data inserted for " + nama);
			}

			rs.close();
			selectPst.close();
			Database.closeConnection();
		} catch (SQLException e) {
			System.out.println("Insert or update failed: " + e.getMessage());
		}
	}

}