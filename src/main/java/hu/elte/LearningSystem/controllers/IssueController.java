package hu.elte.LearningSystem.controllers;

import hu.elte.LearningSystem.entities.Issue;
import hu.elte.LearningSystem.entities.Label;
import hu.elte.LearningSystem.entities.Message;
import hu.elte.LearningSystem.entities.User;
import hu.elte.LearningSystem.repositories.IssueRepository;
import hu.elte.LearningSystem.repositories.LabelRepository;
import hu.elte.LearningSystem.repositories.MessageRepository;
import hu.elte.LearningSystem.repositories.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IssueController {
    
    @Autowired
    private IssueRepository issueRepository;
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private LabelRepository labelRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @RequestMapping("/")
    public String index() {
        return "main"; //main.html
    }
    
    @RequestMapping("/issues")
    public String list(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).get();
        model.addAttribute("title", "Hello");
        model.addAttribute("issues", user.getRole() == User.Role.ROLE_ADMIN
                ? issueRepository.findAll()
                : user.getIssues());
        return "list"; //list.html
    }
    
    @RequestMapping("/issues/{id}")
    public String show(@PathVariable Integer id, Model model, Principal principal) throws Exception {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).get();
        
        Optional<Issue> oIssue = issueRepository.findById(id);
        if (oIssue.isPresent()) {
            Issue issue = oIssue.get();
            
            if (issue.getUser().getId() != user.getId()) {
                throw new Exception("Not authorized");
            }
            
            model.addAttribute("issue", issue);
            model.addAttribute("message", new Message());
            return "issue"; 
        }
        throw new Exception("No such issue");
    }
           
    @GetMapping("/issues/create")
    public String create(Model model) {
        model.addAttribute("issue", new Issue());
        model.addAttribute("allLabels", labelRepository.findAll());
        model.addAttribute("issueLabels", new ArrayList<Integer>());
        return "create-issue"; 
    }
    
    @PostMapping("/issues/create")
    public String store(@Valid Issue issue, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "create-issue";
        }
        
        String username = principal.getName();
        User user = userRepository.findByUsername(username).get();
        
        if (user.getRole() == User.Role.ROLE_USER) {
            issue.setStatus(Issue.Status.NEW);
        }
        issue.setUser(user);
        
        issueRepository.save(issue);
        return "redirect:/issues";
    }
    
    @Secured({ "ROLE_ADMIN" })
    @GetMapping("/issues/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) throws Exception {
        Optional<Issue> oIssue = issueRepository.findById(id);
        if (oIssue.isPresent()) {
            Issue issue = oIssue.get();
            model.addAttribute("issue", issue);
            model.addAttribute("allLabels", labelRepository.findAll());
            
            List<Integer> issueLabels = new ArrayList<>();
            for (Label l : issue.getLabels()) {
                issueLabels.add(l.getId());
            }
            model.addAttribute("issueLabels", issueLabels);
            return "edit-issue"; 
        }
        throw new Exception("No such issue");
    }
    
    @Secured({ "ROLE_ADMIN" })
    @PostMapping("/issues/{id}/edit")
    public String store(@PathVariable Integer id, @Valid Issue issue, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-issue";
        }
        
        Issue issuedb = issueRepository.findById(id).get();
        issue.setUser(issuedb.getUser());
        
        issueRepository.save(issue);
        return "redirect:/issues";
    }
    
    @GetMapping("/issues/{id}/delete")
    public String delete(@PathVariable Integer id) throws Exception {
        issueRepository.deleteById(id);
        return "redirect:/issues";
    }
    
    @PostMapping("/issues/{id}/message")
    public String addMessage(@PathVariable Integer id, @Valid Message message, BindingResult bindingResult, Model model) {
        Optional<Issue> oIssue = issueRepository.findById(id);
        if (oIssue.isPresent()) {
            Issue issue = oIssue.get();
            if (bindingResult.hasErrors()) {
                model.addAttribute("issue", issue);
                model.addAttribute("message", message);
                return "issue"; 
            }
        
            message.setId(null);
            message.setIssue(issue);
            messageRepository.save(message);

            return "redirect:/issues";
        }
        return "redirect:/issues";
    }
}
