package org.alvio.bst.controller;

import org.alvio.bst.model.TreeEntity;
import org.alvio.bst.service.TreeService;
import org.alvio.bst.util.JsonUtils;
import org.alvio.bst.util.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Map;

@Controller
public class TreeController {
    private final TreeService treeService;

    @Autowired
    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/enter-numbers")
    public String showForm(Model model) {
        return "enter-numbers";
    }

    @GetMapping("/previous-trees")
    public String showPreviousTrees(Model model) {
        List<TreeEntity> trees = treeService.getAllTrees();

        // Create a list of maps with formatted JSON
        List<Map<String, String>> displayData = trees.stream()
                .map(tree -> Map.of(
                        "inputNumbers", tree.getInputNumbers(),
                        "isBalanced", String.valueOf(tree.isBalanced()),
                        "treeJson", JsonUtils.toPrettyJson(tree.getTreeJson())
                ))
                .toList();

        model.addAttribute("previousTrees", displayData);
        model.addAttribute("isEmpty", trees.isEmpty());
        return "previous-trees";
    }

    @PostMapping("/process-numbers")
    public String processNumbers(
            @RequestParam String numbers,
            @RequestParam String treeType,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("enteredNumbers", numbers);

        try {
            List<Integer> numberList = NumberUtils.parseNumbers(numbers); // can throw if input is bad

            TreeEntity savedTree;

            if ("balanced".equals(treeType)) {
                savedTree = treeService.buildAndSaveBalancedTree(numberList);
                redirectAttributes.addFlashAttribute("isBalanced", true);
            } else {
                savedTree = treeService.buildAndSaveTree(numberList);
                redirectAttributes.addFlashAttribute("isBalanced", false);
            }

            String treeJson = JsonUtils.toPrettyJson(savedTree.getTreeJson());

            String cleanedInput = numbers.replaceAll("\\s+", "");
            String formattedNumbers = "[" + cleanedInput.replace(",", ", ") + "]";
            redirectAttributes.addFlashAttribute("inputNumbers", formattedNumbers);
            redirectAttributes.addFlashAttribute("treeJson", treeJson);

            // Clear form on success
            redirectAttributes.addFlashAttribute("enteredNumbers", ""); // Clear form on success

        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("error", "Invalid numbers");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Error formatting tree");
        }

        return "redirect:/enter-numbers";
    }

    @GetMapping("/clean-db")
    public String cleanDatabase() {
        treeService.deleteAllTrees(); // Call through service layer
        return "redirect:/previous-trees";
    }

}