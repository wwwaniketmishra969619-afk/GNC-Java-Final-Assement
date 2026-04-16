import java.util.*;

// 1. Custom Exception Class
class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

// 2. Student Model Class
class Student {
    int id;
    String name;
    String email;

    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Email: " + email;
    }
}

// 3. Course Model Class
class Course {
    int id;
    String name;
    double fee;

    public Course(int id, String name, double fee) {
        this.id = id;
        this.name = name;
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Course ID: " + id + " | Name: " + name + " | Fee: " + fee;
    }
}

// 4. Main System Class (Iska naam file name se match hona chahiye)
public class SmartCampus {
    private static List<Student> students = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static Map<Integer, List<Course>> enrollments = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("--- Welcome to SmartCampus System ---");

        while (true) {
            System.out.println("\n1. Add Student\n2. Add Course\n3. Enroll Student\n4. View Enrollments\n5. Exit");
            System.out.print("Select an option: ");
            
            try {
                String input = sc.nextLine();
                int choice = Integer.parseInt(input);

                if (choice == 1) {
                    System.out.print("Enter Student ID (Numbers only): ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    students.add(new Student(id, name, email));
                    System.out.println("Student Added Successfully!");

                } else if (choice == 2) {
                    System.out.print("Enter Course ID: ");
                    int cid = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter Course Name: ");
                    String cname = sc.nextLine();
                    System.out.print("Enter Fee: ");
                    double fee = Double.parseDouble(sc.nextLine());
                    
                    if(fee < 0) throw new InvalidInputException("Error: Fee cannot be negative!");
                    
                    courses.add(new Course(cid, cname, fee));
                    System.out.println("Course Added!");

                } else if (choice == 3) {
                    System.out.print("Enter Student ID to enroll: ");
                    int sid = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter Course ID: ");
                    int cid = Integer.parseInt(sc.nextLine());
                    
                    // Multithreading simulation (Asynchronous processing)
                    Thread t = new Thread(() -> {
                        try {
                            System.out.println("\n[System] Processing enrollment in background...");
                            Thread.sleep(2000); // 2 second ka wait
                            
                            enrollments.putIfAbsent(sid, new ArrayList<>());
                            boolean found = false;
                            for(Course c : courses) {
                                if(c.id == cid) {
                                    enrollments.get(sid).add(c);
                                    found = true;
                                    break;
                                }
                            }
                            if(found) System.out.println("\n[Success] Student " + sid + " enrolled in Course " + cid);
                            else System.out.println("\n[Error] Course ID not found!");

                        } catch(Exception e) {
                            System.out.println("Thread Error: " + e.getMessage());
                        }
                    });
                    t.start();

                } else if (choice == 4) {
                    System.out.println("\n--- Current Enrollments ---");
                    if(enrollments.isEmpty()) System.out.println("No enrollments yet.");
                    enrollments.forEach((sid, clist) -> {
                        System.out.println("Student ID: " + sid + " -> Courses: " + clist);
                    });

                } else if (choice == 5) {
                    System.out.println("Exiting System. Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid option! Try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
        sc.close();
    }
}