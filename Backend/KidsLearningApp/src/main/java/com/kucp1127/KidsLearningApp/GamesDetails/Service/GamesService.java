package com.kucp1127.KidsLearningApp.GamesDetails.Service;


import com.kucp1127.KidsLearningApp.GamesDetails.Model.Games;
import com.kucp1127.KidsLearningApp.GamesDetails.Repository.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GamesService {

    @Autowired
    private GamesRepository gamesRepository;

    // Get all games (optional but handy)
    public List<Games> getAllGames() {
        return gamesRepository.findAll();
    }

    // Get games by standard
    public List<Games> getGamesByGrade(int grade) {
        return gamesRepository.findByGrade(grade);
    }

    // Get games by standard and category
    public List<Games> getGamesByGradeAndCategory(int grade, String category) {
        return gamesRepository.findByGradeAndCategory(grade, category);
    }

    // Get a single game by its ID (gameUrl)
    public Optional<Games> getGameById(String gameUrl) {
        return gamesRepository.findById(gameUrl);
//                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + gameUrl));
    }

    // Create (post) a new game
    public Games createGame(Games game) {
        return gamesRepository.save(game);
    }


}
