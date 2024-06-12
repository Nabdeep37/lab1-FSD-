package com.example.Spring_guess;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WordController {
    @Autowired
    private WordRepository wordRepository;
    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("levels", new String[]{"Easy", "Medium", "Hard"});
        model.addAttribute("selectedLevel", "");
        return "wordForm";
    }
    @PostMapping("/word")
    public String getWord(@ModelAttribute("selectedLevel") String selectedLevel,
                          Model model, HttpSession session) {
        Word word = wordRepository.findRandomWordByLevel(selectedLevel);
        model.addAttribute("word", word);
        session.setAttribute("word", word);
// return "wordDetails";
        return "redirect:/showWord";
    }
    @GetMapping("/showWord")
    public String showWord(HttpSession session, Model model) {
        Word wordarray = (Word) session.getAttribute("word");
        model.addAttribute("GivenHints", wordarray.getHints());
        return "wordInput";
    }
    @PostMapping("/getWord")
    public String login(@RequestParam String word, HttpSession session, Model
            model) {
        Word wordarray = (Word) session.getAttribute("word");
        model.addAttribute("GivenHints", wordarray.getHints());
        if(word != null && wordarray.getWord().equals(word)) {
            model.addAttribute("message", "Congratulations You win");
            return "wordInput";
        } else {
            model.addAttribute("message", "Sorry!! You Loose");
            return "wordInput";
        }
    }
}
