# Instructor Code Review Feedback

**Contact**: Dr. Yiji Zhang (yiji.zhang@northwestern.edu)

**Purpose of This Document**:
The instructor will perform code review with respect to software design, error handling, format and style on the main branch every week starting Week 6 using the letter grade A standards.
The following chapters of the textbook are considered: Chapter 1, 2, 3, 4, 5, 6, 7, 9, and 10. The corresponding lectures are considered, too.


## Week 7 Code Review
Overall, good job! A couple of comments:

1. TDD process and requirements: I noticed that some of the commits (e.g. [ec03d516bf521f9ab060ee6fe1dbbfe4e5baf38d](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-8/commit/ec03d516bf521f9ab060ee6fe1dbbfe4e5baf38d), [98a8a7e7cc81cb995b840b203724f89f231e162c](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-8/commit/98a8a7e7cc81cb995b840b203724f89f231e162c)) failed Gradle Build. This is a violation of what Uncle Bob argues in the textbook. He argues that every commit should pass the build so that no rollback will lead to a broken system. To avoid this, always run gradle build locally to make sure everything passes because you do git commit.
2. In the Intersections class, you have multiple null checks. That is against what Uncle Bob argues as well. So here are a couple of things to consider:
1) indeed, it is generally a good practice to do "defense programming". Your null check is a great example.
2) However, what uncle Bob argues in the book is that instead of doing null check everywhere, especially in a language like Java, we should make sure we don't pass null around, period.
3) If you work on an API or a library that is designed to be used by others, the null check is more important. Or, if you work in a language like C or C++ where there is pointer allowed, it is more important to check for null explicitly and thoroughly.

3. Also in Intersections class, getNeighboringHexes method,

```
        for (int i = 0; i < neighboringHexes.size(); ++i) {
            System.out.println(neighboringHexes.get(i));
        }
```
is a violation of the MVC pattern you are required to use, as printing to the console is a "View" responsibility. If the printing was meant for debugging, then it should be deleted from the codebase.

4. Error handling code, like in the Game constructor,
```
        if (players == null || players.size() < 2) {
            throw new IllegalArgumentException("Error: Game must have at least 2 players");
        }
        if (players.size() > 4) {
            throw new IllegalArgumentException("Error: Game cannot have more than 4 players");
        }
```
should be put into one or more private methods with good method names for better readability.

## Week 6 Code Review
I have read every line of production code currently in the main branch. There's not much code yet so I will do another code review next week.

And it seems to me from the branches and the commit history that not all the members have been contributing to the project. Hope the teamwork is going well and let me know if you team runs into any issues!
