package megamek.common;

import megamek.server.victory.VictoryResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class GameTest {

    @Test
    public void testCancelVictory() {
        // Default test
        Game game = new Game();
        game.cancelVictory();
        assertFalse(game.isForceVictory());
        assertSame(Player.PLAYER_NONE, game.getVictoryPlayerId());
        assertSame(Player.TEAM_NONE, game.getVictoryTeam());

        // Test with members set to specific values
        Game game2 = new Game();
        game2.setVictoryPlayerId(10);
        game2.setVictoryTeam(10);
        game2.setForceVictory(true);

        game2.cancelVictory();
        assertFalse(game.isForceVictory());
        assertSame(Player.PLAYER_NONE, game.getVictoryPlayerId());
        assertSame(Player.TEAM_NONE, game.getVictoryTeam());
    }

    @Test
    public void testGetVictoryReport() {
        Game game = new Game();
        game.createVictoryConditions();
        VictoryResult victoryResult = game.getVictoryResult();
        assertNotNull(victoryResult);

        // Note: this accessors are tested in VictoryResultTest
        assertSame(Player.PLAYER_NONE, victoryResult.getWinningPlayer());
        assertSame(Player.TEAM_NONE, victoryResult.getWinningTeam());

        int winningPlayer = 2;
        int winningTeam = 5;

        // Test an actual scenario
        Game game2 = new Game();
        game2.setVictoryTeam(winningTeam);
        game2.setVictoryPlayerId(winningPlayer);
        game2.setForceVictory(true);
        game2.createVictoryConditions();
        VictoryResult victoryResult2 = game2.getVictoryResult();

        assertSame(winningPlayer, victoryResult2.getWinningPlayer());
        assertSame(winningTeam, victoryResult2.getWinningTeam());
    }

    @Test
    public void testUpdatePlayersRating() {
        String playerName = "jefke";
        Player player1 = new Player(1, playerName);
        player1.setRating(50);

        playerName = "Jeanke";
        Player player2 = new Player(2, playerName);
        player2.setRating(125);

        playerName = "Paul";
        Player player3 = new Player(3, playerName);
        player3.setRating(75);

        playerName = "Steven";
        Player player4 = new Player(4, playerName);
        player4.setRating(100);

        Game game = new Game();
        game.addPlayer(1, player1);
        game.addPlayer(2, player2);
        game.addPlayer(3, player3);
        game.addPlayer(4, player4);
        game.end(1, Player.TEAM_NONE);

        assertTrue(player1.getRating() > 50);
        assertTrue(player2.getRating() < 125);
        assertTrue(player3.getRating() < 75);
        assertTrue(player4.getRating() < 100);

        assertEquals(100, player1.getRating(), 0.0);
        assertEquals(50, player2.getRating(), 0.0);
        assertEquals(50, player3.getRating(), 0.0);
        assertEquals(50, player4.getRating(), 0.0);
    }

    @Test
    public void testUpdatePlayersRatingDraw() {
        String playerName = "jefke";
        Player player1 = new Player(1, playerName);
        player1.setRating(50);

        playerName = "Jeanke";
        Player player2 = new Player(2, playerName);
        player2.setRating(125);

        playerName = "Paul";
        Player player3 = new Player(3, playerName);
        player3.setRating(75);

        playerName = "Steven";
        Player player4 = new Player(4, playerName);
        player4.setRating(100);

        Game game = new Game();
        game.addPlayer(1, player1);
        game.addPlayer(2, player2);
        game.addPlayer(3, player3);
        game.addPlayer(4, player4);
        game.end(Player.PLAYER_NONE, Player.TEAM_NONE);

        assertEquals(50, player1.getRating(), 0.0);
        assertEquals(125, player2.getRating(), 0.0);
        assertEquals(75, player3.getRating(), 0.0);
        assertEquals(100, player4.getRating(), 0.0);
    }

    @Test
    public void testUpdatePlayersRatingWinnerAlreadyHasBetterRanking() {
        String playerName = "jefke";
        Player player1 = new Player(1, playerName);
        player1.setRating(500);

        playerName = "Jeanke";
        Player player2 = new Player(2, playerName);
        player2.setRating(125);

        playerName = "Paul";
        Player player3 = new Player(3, playerName);
        player3.setRating(75);

        playerName = "Steven";
        Player player4 = new Player(4, playerName);
        player4.setRating(100);

        Game game = new Game();
        game.addPlayer(1, player1);
        game.addPlayer(2, player2);
        game.addPlayer(3, player3);
        game.addPlayer(4, player4);
        game.end(1, Player.TEAM_NONE);

        assertTrue(player1.getRating() > 500);
        assertTrue(player2.getRating() < 125);
        assertTrue(player3.getRating() < 75);
        assertTrue(player4.getRating() < 100);

        assertEquals(540, player1.getRating(), 0.0);
        assertEquals(78.125, player2.getRating(), 0.0);
        assertEquals(43.125, player3.getRating(), 0.0);
        assertEquals(60, player4.getRating(), 0.0);
    }

    @Test
    public void testUpdateTeamRating() {
        String playerName = "jefke";
        Player player1 = new Player(1, playerName);
        player1.setRating(50);

        playerName = "Jeanke";
        Player player2 = new Player(2, playerName);
        player2.setRating(125);

        playerName = "Paul";
        Player player3 = new Player(3, playerName);
        player3.setRating(75);

        playerName = "Steven";
        Player player4 = new Player(4, playerName);
        player4.setRating(100);

        Team team1 = new Team(1);
        Team team2 = new Team(2);
        player1.setTeam(team1.getId());
        player2.setTeam(team1.getId());
        player3.setTeam(team2.getId());
        player4.setTeam(team2.getId());

        Game game = new Game();
        game.addPlayer(1, player1);
        game.addPlayer(1, player2);
        game.addPlayer(2, player3);
        game.addPlayer(2, player4);
        game.end(Player.PLAYER_NONE, team1.getId());

        assertTrue(player1.getRating() > 50);
        assertTrue(player2.getRating() > 125);
        assertTrue(player3.getRating() < 75);
        assertTrue(player4.getRating() < 100);
    }
}
