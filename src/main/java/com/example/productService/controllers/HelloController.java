package com.example.productService.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {


    /*ye ek path variable ka example hai isme hume jo bhi value helloworld ke baad receive hui wo path variable hoti hai
     * wo hamara path ban jati hai jaise isme name and time hamara path tha
     * localhost:8080/api/helloWorld/nikhil/10 ye mera url bana tha
     */

    @GetMapping("/helloWorld/{name}/{times}")
    public String firstApi(@PathVariable String name, @PathVariable ("times") int times){
        StringBuilder output = new StringBuilder();
        for(int i = 1;i<=times;i++){
            output.append("hello world ").append(name).append("\n");
        }
        return output.toString();
    }

    @GetMapping("/queryParam")
    public String getNameByQueryParam(@RequestParam("name") String name){
        return "Hello " + name;
    }

    @GetMapping("/queryPathParam/{name}")
    public String getNameByQueryAndPathParam(@PathVariable("name") String name,@RequestParam ("id") int id){
        return "Hello " + name +" " +id;
    }
}
