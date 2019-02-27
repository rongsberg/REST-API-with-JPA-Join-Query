- follow https://spring.io/guides/tutorials/rest/

- fix controller to add imports:
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
- fix pom.xml to add hateoas dependency
- fix controller: import java.util.stream.Collectors
-fix get mapping annotation, change to following in order to fix mashalling errors:
@GetMapping(path="/employees", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
- add import org.springframework.http.MediaType
