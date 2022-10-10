package vn.techmaster.course;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.techmaster.course.entity.Course;
import vn.techmaster.course.entity.Image;
import vn.techmaster.course.entity.Topic;
import vn.techmaster.course.entity.User;
import vn.techmaster.course.repository.CourseRepository;
import vn.techmaster.course.repository.ImageRepository;
import vn.techmaster.course.repository.TopicRepository;
import vn.techmaster.course.repository.UserRepository;

import java.util.*;

@SpringBootTest
class InitDataTest {
    @Autowired
    private Faker faker;

    @Autowired
    private Slugify slugify;

    @Autowired
    private Random random;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Test
    void save_user() {
        for (int i = 0; i < 3; i++) {
            User user = User.builder()
                    .name(faker.name().fullName())
                    .email(faker.internet().emailAddress())
                    .phone(faker.phoneNumber().cellPhone())
                    .avatar(faker.avatar().image())
                    .build();
            userRepository.save(user);
        }
    }

    @Test
    void save_topic() {
        for (int i = 0; i < 3; i++) {
            Topic topic = Topic.builder()
                    .name(faker.leagueOfLegends().rank())
                    .build();
            topicRepository.save(topic);
        }
    }

    @Test
    void save_image() {
        for (int i = 0; i < 30; i++) {
            String genarateFileId = UUID.randomUUID().toString();
            Image image = Image.builder()
                    .id(genarateFileId)
                    .link(faker.company().logo())
                    .build();
            imageRepository.save(image);
        }
    }

    @Test
    void save_course() {
        // Lấy danh sách image
        List<Image> images = imageRepository.findAll();

        // Lấy danh sách user
        List<User> users = userRepository.findAll();

        // Lấy danh sách topic
        List<Topic> topics = topicRepository.findAll();

        for (int i = 0; i < 12; i++) {
            String name = faker.company().name();

            // Random image
            String imageRd = images.get(random.nextInt(images.size())).getLink();

            // Random user
            User userRd = users.get(random.nextInt(users.size()));

            // Danh sách topic
            Set<Topic> topicsRd = new LinkedHashSet<>();

            for (int j = 0; j < 3; j++) {
                Topic topicRd = topics.get(random.nextInt(topics.size()));
                topicsRd.add(topicRd);
            }

            Course course = Course.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .type(random.nextInt(2) == 1 ? "onlab" : "online")
                    .description(faker.lorem().sentence(100))
                    .thumbnail(imageRd)
                    .user(userRd)
                    .topics(topicsRd)
                    .build();
            courseRepository.save(course);
        }
    }
}
