package com.alura.forohub.controller;

import com.alura.forohub.domain.Topic;
import com.alura.forohub.domain.TopicListData;
import com.alura.forohub.domain.TopicRegisterData;
import com.alura.forohub.domain.TopicUpdateData;
import com.alura.forohub.repository.TopicRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @PostMapping
    @Transactional
    public void registerTopic(@RequestBody @Valid TopicRegisterData data){
        topicRepository.save(new Topic(data));
    }

    @GetMapping
    public Page<TopicListData> getAllTopics(@PageableDefault(size=2, page = 0, sort = "author") Pageable pagination) {
        return topicRepository.findAll(pagination).map(TopicListData::new);

    }

    @GetMapping("/recents")
    public List<TopicListData> getRecentsTopics() {
        return topicRepository.findByOrderByCreatedAtDesc(Limit.of(2))
                .stream()
                .map(TopicListData::new)
                .toList();
    }

    @GetMapping("/search/{course}")
    public List<TopicListData> getByCourse(@PathVariable String course) {
        return topicRepository.findByCourseContaining(course)
                .stream()
                .map(TopicListData::new)
                .toList();
    }
    @GetMapping("/search/year/{year}")
    public List<TopicListData> getByYearPub(@PathVariable Integer year) {
        return topicRepository.findByCreatedAtYear(year)
                .stream()
                .map(TopicListData::new)
                .toList();
    }

    //Implementar pageable default


    //Detallando un topico
    @GetMapping("/{id}")
    public TopicListData getTopicDetails(@PathVariable Long id) {
        return new TopicListData(topicRepository.findById(id).orElseGet(Topic::new));
    }

    @PutMapping
    @Transactional
    public TopicListData updateTopic(@RequestBody @Valid TopicUpdateData data) {
    Topic topicToUpdate = topicRepository.getReferenceById(data.id());
        topicToUpdate.updateTopic(data);

        return new TopicListData(topicToUpdate);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteTopic(@PathVariable Long id) {
        Topic topic = topicRepository.getReferenceById(id);
        topicRepository.delete(topic);
    }
}
