import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SecretDetective {

    public String recoverSecret(char[][] triplets) {
        final Map<Character, Set<Character>> map = new HashMap<>();
        final Set<Character> characters = new HashSet<>();
        for (char[] triplet : triplets) {
            for (char character : triplet) {
                characters.add(character);
            }
            map.merge(triplet[0],
                      new HashSet<>(Arrays.asList(triplet[1], triplet[2])),
                      (existingChars, newChars) -> {
                          existingChars.addAll(newChars);
                          return existingChars;
                      });
            map.merge(triplet[1],
                      new HashSet<>(Collections.singletonList(triplet[2])),
                      (existingChars, newChars) -> {
                          existingChars.addAll(newChars);
                          return existingChars;
                      });
        }

        StringBuilder string = new StringBuilder();
        Character lastChar = findLastChar(map, characters);
        removeResolvedChar(map, string, lastChar);

        while (!map.isEmpty()) {
            for (Map.Entry<Character, Set<Character>> entry : map.entrySet()) {
                if (entry
                        .getValue()
                        .isEmpty()) {
                    lastChar = entry.getKey();
                    map.remove(lastChar);
                    removeResolvedChar(map, string, lastChar);
                    break;
                }
            }
        }

        return string
                   .reverse()
                   .toString();
    }

    private void removeResolvedChar(Map<Character, Set<Character>> map,
                                    StringBuilder string,
                                    Character lastChar) {
        string.append(lastChar);
        for (Set<Character> characterSet : map.values()) {
            characterSet.remove(lastChar);
        }
    }

    private Character findLastChar(Map<Character, Set<Character>> map, Set<Character> characters) {
        characters.removeAll(map.keySet());
        return characters
                   .iterator()
                   .next();
    }

}