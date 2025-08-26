// package com.app.resourceforge.Initializer;

// import java.util.Arrays;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// import com.app.resourceforge.model.Role;
// import com.app.resourceforge.model.SubscriptionPlanMaster;
// import com.app.resourceforge.model.User;
// import com.app.resourceforge.repository.CompanyRepository;
// import com.app.resourceforge.repository.RoleRepository;
// import com.app.resourceforge.repository.SubscriptionPlanMasterRepository;
// import com.app.resourceforge.repository.SubscriptionPlanRepository;
// import com.app.resourceforge.repository.UserRepository;

// @Component
// public class DatabaseInitializer implements CommandLineRunner {

//     @Autowired
//     PasswordEncoder passwordEncoder;

//     private final UserRepository userRepository;
//     private final RoleRepository roleRepository;
//     private final CompanyRepository companyRepository;
//     private final SubscriptionPlanRepository subscriptionPlanRepository;
//     private final SubscriptionPlanMasterRepository subscriptionPlanMasterRepository;

//     public DatabaseInitializer(UserRepository userRepository, RoleRepository roleRepository,
//             CompanyRepository companyRepository,
//             SubscriptionPlanRepository subscriptionPlanRepository,
//             SubscriptionPlanMasterRepository subscriptionPlanMasterRepository) {
//         this.userRepository = userRepository;
//         this.roleRepository = roleRepository;
//         this.companyRepository = companyRepository;
//         this.subscriptionPlanRepository = subscriptionPlanRepository;
//         this.subscriptionPlanMasterRepository = subscriptionPlanMasterRepository;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         List<Role> roles = Arrays.asList(
//                 new Role(null, "VARELI", true),
//                 new Role(null, "SUPERADMIN", true),
//                 new Role(null, "ADMIN", true),
//                 new Role(null, "USER", true));
//         roles.forEach(role -> roleRepository.save(role));

//         List<User> users = Arrays.asList(
//                 new User("Vareli", passwordEncoder.encode("vareli"), "VARELI", "vareli",
//                         "info@vareli.co.in", "03324660248", 0, 0, true, true));
//         users.forEach(user -> userRepository.save(user));

//         List<SubscriptionPlanMaster> plans = Arrays.asList(
//                 new SubscriptionPlanMaster(null, "Basic", 50, 1, 2, 10, 100.00, false, false, 1, "Basic Plan", false),
//                 new SubscriptionPlanMaster(null, "Pro", 1000, 5, 4, 20, 1000.00, true, true, 1, "Pro Plan", false),
//                 new SubscriptionPlanMaster(null, "Advance", 10000, 10, 2, 50, 10000.00, true, true, 1, "Advance Plan",
//                         false));

//         plans.forEach(plan -> subscriptionPlanMasterRepository.save(plan));
//     }
// }
