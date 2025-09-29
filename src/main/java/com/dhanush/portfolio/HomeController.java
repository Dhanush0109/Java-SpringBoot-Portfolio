package com.dhanush.portfolio;

import java.util.List;
import com.dhanush.portfolio.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

   /* @GetMapping("/projects")
    public String projects(Model model){

        List<Project> projectList = projectRepository.findAll();
//        List<Project> projectList = List.of(new Project("Personal Website",
//                        "A responsive portfolio website built with Spring Boot and Thymeleaf.",
//                        "https://github.com/yourusername/personal-website",
//                        "/images/portfolio.jpeg"),
//                new Project("To-Do App",
//                        "A Java-based to-do list app with CRUD functionality.",
//                        null,
//                        "/images/ToDo.jpg"),
//                new Project("Weather Dashboard",
//                        "View the current weather in any city with this REST API project.",
//                        "https://github.com/yourusername/weather-dashboard",
//                        "/images/weather.jpeg"));
        model.addAttribute("projects", projectList);
        return "projects";
    }*/

    @GetMapping("/projects")
    public String projects(@RequestParam(required = false) String keyword, Model model) {
        List<Project> projectList;
        if (keyword != null && !keyword.isEmpty()) {
            projectList = projectRepository.findByTitleContainingIgnoreCase(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            projectList = projectRepository.findAll();
            model.addAttribute("keyword", "");
        }
        model.addAttribute("projects", projectList);
        return "projects";
    }


    @GetMapping("/projects/add")
    public String showAddProjectForm(Model model) {
        model.addAttribute("project", new Project());
        return "add-project";
    }

    @PostMapping("/projects/add")
    public String addProject(@ModelAttribute Project project) {
        projectRepository.save(project);
        return "redirect:/projects";
    }

    @PostMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectRepository.deleteById(id);
        return "redirect:/projects";
    }

    // Show edit form
    @GetMapping("/projects/edit/{id}")
    public String showEditProjectForm(@PathVariable Long id, Model model) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project id: " + id));
        model.addAttribute("project", project);
        return "edit-project";
    }

    // Handle edit form submission
    @PostMapping("/projects/edit/{id}")
    public String editProject(@PathVariable Long id, @ModelAttribute Project project) {
        project.setId(id); // Ensure we update the right project
        projectRepository.save(project);
        return "redirect:/projects";
    }

    @GetMapping("/projects/view/{id}")
    public String viewProject(@PathVariable Long id, Model model) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project id: " + id));
        model.addAttribute("project", project);
        return "project-details";
    }



}
