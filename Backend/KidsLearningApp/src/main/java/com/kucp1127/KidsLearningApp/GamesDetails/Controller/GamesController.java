package com.kucp1127.KidsLearningApp.GamesDetails.Controller;



import com.kucp1127.KidsLearningApp.GamesDetails.Service.GamesService;
import com.kucp1127.KidsLearningApp.GamesDetails.Model.Games;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*")
public class GamesController {

    @Autowired
    private GamesService gamesService;

    // (Optional) GET all games
    @GetMapping
    public List<Games> getAllGames() {
        return gamesService.getAllGames();
    }

    // GET games by standard
    @GetMapping("/standard/{standard}")
    public List<Games> getGamesByStandard(@PathVariable int standard) {
        return gamesService.getGamesByGrade(standard);
    }

    // GET games by standard and category
    @GetMapping("/standard/{standard}/category/{category}")
    public List<Games> getGamesByStandardAndCategory(@PathVariable int standard,
                                                     @PathVariable String category) {
        return gamesService.getGamesByGradeAndCategory(standard, category);
    }

    // GET game by ID (gameUrl)
    @GetMapping("/{gameUrl}")
    public Optional<Games> getGameById(@PathVariable String gameUrl) {
        return gamesService.getGameById(gameUrl);
    }

    // POST a new game
    @PostMapping
    public Games createGame(@RequestBody Games game) {
        return gamesService.createGame(game);
    }
}
