package com.skillbox.webshop.controller;

import com.skillbox.webshop.exception.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("item")
public class ItemController {

    private int counter = 4;
    private List<Map<String, String>> items = new ArrayList<>() {{
        add(new HashMap<>() {{
            put("id", "1");
            put("text", "first message");
        }});
        add(new HashMap<>() {{
            put("id", "2");
            put("text", "second message");
        }});
        add(new HashMap<>() {{
            put("id", "3");
            put("text", "third message");
        }});
    }};

    @GetMapping
    public List<Map<String, String>> list() {
        return items;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getId(id);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> item) {
        item.put("id", String.valueOf(counter++));
        items.add(item);
        return item;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> item) {
        Map<String, String> itemFromDB = getId(id);
        itemFromDB.putAll(item);
        itemFromDB.put("id", id);
        return itemFromDB;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> item = getId(id);
        items.remove(item);
    }

    private Map<String, String> getId(String id) {
        return items.stream().filter(item -> item.get("id").equals(id)).findFirst().orElseThrow(NotFoundException::new);
    }
}
