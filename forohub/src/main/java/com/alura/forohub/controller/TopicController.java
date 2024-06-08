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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Topic> registerTopic
            (@RequestBody @Valid TopicRegisterData data,
             UriComponentsBuilder uriComponentBuilder){
        Topic topic = topicRepository.save(new Topic(data));

        URI url = uriComponentBuilder.path("/medicos/{id}")
                .buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(url).body(topic);
    }

    @GetMapping
    public ResponseEntity<Page<TopicListData>> getAllTopics(@PageableDefault(size=10, page = 0, sort = "author", direction = Sort.Direction.DESC) Pageable pagination) {
        return ResponseEntity.ok(topicRepository.findAll(pagination).map(TopicListData::new));

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
    public ResponseEntity<TopicListData> getTopicDetails(@PathVariable Long id) {
        return ResponseEntity.ok(new TopicListData(topicRepository.findById(id).orElseGet(Topic::new)));
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateTopic(@RequestBody @Valid TopicUpdateData data) {
        Topic topicToUpdate = topicRepository.getReferenceById(data.id());
        topicToUpdate.updateTopic(data);
        return ResponseEntity.ok(new TopicListData(topicToUpdate));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteTopic(@PathVariable Long id) {
        Topic topic = topicRepository.getReferenceById(id);
        topicRepository.delete(topic);
        return ResponseEntity.noContent().build();
    }
}
