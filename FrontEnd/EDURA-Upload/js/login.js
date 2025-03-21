// Base URL constant
const baseUrl = "http://iiit1127.us-east-2.elasticbeanstalk.com";

// Toast message function
function showToast(message, type) {
  const toast = document.getElementById("toast");
  toast.textContent = message;
  toast.className = `toast ${type}`;
  setTimeout(() => toast.classList.add("show"), 10);
  setTimeout(() => { toast.classList.remove("show"); }, 3000);
}

// JWT token storage functions
function setAuthToken(token) {
  localStorage.setItem("token", token);
}

function getAuthToken() {
  return localStorage.getItem("token");
}

function createAuthHeaders() {
  const token = getAuthToken();
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
  };
}

// Function to show/hide forms
function showForm(formType) {
  const loginForm = document.getElementById("loginForm");
  const registerForm = document.getElementById("registerForm");
  if (formType === "login") {
    loginForm.style.display = "flex";
    registerForm.style.display = "none";
  } else {
    loginForm.style.display = "none";
    registerForm.style.display = "flex";
  }
}

// Function to redirect based on grade
function redirectToGradePage(grade) {
  const gradePages = {
    "0": "/landing/landingK.html",   // Kindergarten
    "1": "/landing/landing1.html",     // Grade 1
    "2": "/landing/landing2.html",     // Grade 2
    "3": "/landing/landing3.html",     // Grade 3
    "4": "/landing/landing4.html",     // Grade 4
    "5": "/landing/landing5.html",     // Grade 5
    "-1": "/landing/parentDashboard.html" // Parents Dashboard
  };

  if (grade >= 0 && grade <= 5) {
    window.location.href = gradePages[String(grade)] || "/landing/landingK.html";
  } else if (grade === -1) {
    window.location.href = gradePages["-1"];
  } else if (grade === -10) {
    showToast("Invalid username. Please try again.", "error");
  } else {
    showToast("Unexpected grade received.", "error");
  }
}

// Toggle password visibility
function togglePasswordVisibility(inputId, toggleEl) {
  const passwordInput = document.getElementById(inputId);
  const eyeIcon = toggleEl.querySelector('.eye-icon');
  if (passwordInput.type === "password") {
    passwordInput.type = "text";
    eyeIcon.innerHTML = `
      <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
      <circle cx="12" cy="12" r="3"></circle>
      <line x1="1" y1="1" x2="23" y2="23" class="eye-slash"></line>
    `;
  } else {
    passwordInput.type = "password";
    eyeIcon.innerHTML = `
      <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
      <circle cx="12" cy="12" r="3"></circle>
    `;
  }
}

// Check if a username already exists (for registration)
async function checkUsernameExists(username) {
  try {
    const response = await fetch(`${baseUrl}/find/${username}`, {
      method: "GET",
      headers: { "Content-Type": "application/json" }
    });
    if (!response.ok) {
      throw new Error(`Server error: ${response.status}`);
    }
    return await response.json();
  } catch (error) {
    console.error("Error checking username:", error);
    return false;
  }
}

// Validate parent username during registration
async function validateParentUsername() {
  const usernameInput = document.getElementById("username");
  const username = usernameInput.value.trim();
  if (!username) return false;
  const exists = await checkUsernameExists(username);
  if (exists) {
    showToast("Username already exists. Please choose another.", "error");
    usernameInput.classList.add("error");
    return false;
  } else {
    usernameInput.classList.remove("error");
    return true;
  }
}

// Validate child username during registration
async function validateChildUsername(input) {
  const username = input.value.trim();
  if (!username) return false;
  const exists = await checkUsernameExists(username);
  if (exists) {
    showToast("Child username already exists. Please choose another.", "error");
    input.classList.add("error");
    return false;
  } else {
    input.classList.remove("error");
    return true;
  }
}

// Functions for handling child forms in registration
let childCount = 0;
function createChildForm() {
  childCount++;
  const childDiv = document.createElement("div");
  childDiv.className = "children-container";
  childDiv.setAttribute('data-child-number', `Child ${childCount}`);
  const childContent = `
    <div class="form-group">
      <label>Child Username</label>
      <input type="text" class="child-username" required>
    </div>
    <div class="form-group">
      <label>Child Name</label>
      <input type="text" class="child-name" required>
    </div>
    <div class="form-group">
      <label>Child Password</label>
      <div class="password-container">
        <input type="password" class="child-password" required>
        <span class="password-toggle" onclick="togglePasswordVisibility('child-password-${childCount}', this)">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="eye-icon">
            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
            <circle cx="12" cy="12" r="3"></circle>
          </svg>
        </span>
      </div>
    </div>
    <div class="form-group">
      <label>Child Age</label>
      <input type="number" class="child-age" required min="1" max="17">
    </div>
    <div class="form-group">
      <label>Grade</label>
      <select class="child-grade" required>
        <option value="" disabled selected>Select Grade</option>
        <option value="0">Kindergarten</option>
        <option value="1">Grade 1</option>
        <option value="2">Grade 2</option>
        <option value="3">Grade 3</option>
        <option value="4">Grade 4</option>
        <option value="5">Grade 5</option>
      </select>
    </div>
    <button type="button" class="remove-child-btn">Remove Child</button>
  `;
  childDiv.innerHTML = childContent;
  const passwordField = childDiv.querySelector('.child-password');
  passwordField.id = `child-password-${childCount}`;
  const usernameField = childDiv.querySelector('.child-username');
  usernameField.addEventListener('blur', function() {
    validateChildUsername(this);
  });
  const removeButton = childDiv.querySelector(".remove-child-btn");
  removeButton.addEventListener("click", () => {
    childDiv.remove();
    updateChildNumbers();
  });
  return childDiv;
}

function updateChildNumbers() {
  const childContainers = document.querySelectorAll('.children-container');
  childContainers.forEach((container, index) => {
    const newIndex = index + 1;
    container.setAttribute('data-child-number', `Child ${newIndex}`);
    const passwordField = container.querySelector('.child-password');
    if (passwordField) {
      passwordField.id = `child-password-${newIndex}`;
      const toggleButton = container.querySelector('.password-toggle');
      if (toggleButton) {
        toggleButton.setAttribute('onclick', `togglePasswordVisibility('child-password-${newIndex}', this)`);
      }
    }
  });
  childCount = childContainers.length;
}

function addChild() {
  const childrenList = document.getElementById("childrenList");
  const childElement = createChildForm();
  childrenList.appendChild(childElement);
}

// Store child data in localStorage
function storeChildData(children) {
  let childData = JSON.parse(localStorage.getItem("childrenData") || "{}");
  children.forEach(child => {
    childData[child.username] = {
      name: child.name,
      grade: child.grade
    };
  });
  localStorage.setItem("childrenData", JSON.stringify(childData));
}

// Updated handleLogin function with new API call for parents and child logins
async function handleLogin(event) {
  event.preventDefault();
  const username = document.getElementById("loginUsername").value.trim();
  const password = document.getElementById("loginPassword").value.trim();
  if (!username || !password) {
    showToast("Username and password are required", "error");
    return;
  }
  try {
    // Step 1: Check if username exists
    const userResponse = await fetch(`${baseUrl}/find/${username}`, {
      method: "GET",
      headers: { "Content-Type": "application/json" }
    });
    if (!userResponse.ok) throw new Error(`Server error: ${userResponse.status}`);
    const usernameExists = await userResponse.json();
    if (!usernameExists) {
      showToast("Username not found", "error");
      return;
    }
    // Step 2: Validate password
    const loginResponse = await fetch(`${baseUrl}/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });
    if (!loginResponse.ok) {
      showToast("Invalid username or password", "error");
      return;
    }
    const token = await loginResponse.text();
    if (!token || token === "Invalid username or password" || token === "failure") {
      showToast("Invalid username or password", "error");
      return;
    }
    localStorage.setItem("token", token);
    localStorage.setItem("parentUsername", username);
    // Step 3: Fetch grade
    const gradeResponse = await fetch(`${baseUrl}/grade/${encodeURIComponent(username)}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      }
    });
    if (!gradeResponse.ok) {
      throw new Error(`Server error: ${gradeResponse.status}`);
    }
    const gradeData = await gradeResponse.text();
    console.log("Fetched Grade Data:", gradeData);
    if (!gradeData) {
      showToast("Grade not found. Redirecting to default page.", "error");
      setTimeout(() => {
        window.location.href = "/landing/default.html";
      }, 1000);
      return;
    }
    const grade = parseInt(gradeData, 10);
    localStorage.setItem("currentUserGrade", grade);
    
    if (grade === -1) {
      // For parent login, fetch child list and store first child username
      fetch(`${baseUrl}/getChild/${encodeURIComponent(username)}`)
        .then(response => response.json())
        .then(childList => {
          if (childList && Array.isArray(childList) && childList.length > 0) {
            localStorage.setItem("loginUsername", childList[0]);
          }
          redirectToGradePage(grade);
        })
        .catch(error => {
          console.error("Error fetching child data:", error);
          redirectToGradePage(grade);
        });
    } else if (grade >= 0 && grade <= 5) {
      // For child login, store the entered username as loginUsername
      localStorage.setItem("loginUsername", username);
      redirectToGradePage(grade);
    } else if (grade === -10) {
      showToast("Invalid username. Please try again.", "error");
    } else {
      showToast("Unexpected grade received.", "error");
    }
    showToast("Successfully logged in", "success");
  } catch (error) {
    console.error("Login Error:", error);
    showToast(error.message || "An error occurred during login.", "error");
  }
}

// Example functions for protected API calls (if needed)
function fetchProtectedResource(endpoint) {
  return fetch(`${baseUrl}/${endpoint}`, {
    method: "GET",
    headers: createAuthHeaders()
  })
  .then(response => {
    if (!response.ok) {
      if (response.status === 401) {
        localStorage.removeItem("token");
        window.location.href = "index.html";
        throw new Error("Session expired. Please log in again.");
      }
      throw new Error(`Server error: ${response.status}`);
    }
    return response.json();
  });
}

function postToProtectedEndpoint(endpoint, data) {
  return fetch(`${baseUrl}/${endpoint}`, {
    method: "POST",
    headers: createAuthHeaders(),
    body: JSON.stringify(data)
  })
  .then(response => {
    if (!response.ok) {
      if (response.status === 401) {
        localStorage.removeItem("token");
        window.location.href = "index.html";
        throw new Error("Session expired. Please log in again.");
      }
      throw new Error(`Server error: ${response.status}`);
    }
    return response.json();
  });
}

// Registration handler
async function handleRegistration(event) {
  event.preventDefault();
  const isParentUsernameValid = await validateParentUsername();
  if (!isParentUsernameValid) {
    return;
  }
  const childUsernameInputs = document.querySelectorAll(".child-username");
  for (const input of childUsernameInputs) {
    const isChildUsernameValid = await validateChildUsername(input);
    if (!isChildUsernameValid) {
      return;
    }
  }
  const children = [];
  document.querySelectorAll(".children-container").forEach((childDiv) => {
    children.push({
      username: childDiv.querySelector(".child-username").value,
      name: childDiv.querySelector(".child-name").value,
      password: childDiv.querySelector(".child-password").value,
      age: Number.parseInt(childDiv.querySelector(".child-age").value),
      grade: childDiv.querySelector(".child-grade").value,
    });
  });
  const registrationData = {
    username: document.getElementById("username").value,
    fullName: document.getElementById("fullName").value,
    email: document.getElementById("email").value,
    password: document.getElementById("password").value,
    children: children,
  };
  storeChildData(children);
  fetch(`${baseUrl}/register`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(registrationData),
  })
      .then((response) => {
        if (response.ok) {
          showToast("Registration successful!", "success");
          document.querySelector("form").reset();
          document.getElementById("childrenList").innerHTML = "";
          childCount = 0;
          setTimeout(() => showForm("login"), 1500);
        } else {
          showToast("Registration failed. Please try again.", "error");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        showToast("An error occurred during registration.", "error");
      });
}

// Add event listener for parent username validation on registration form
document.addEventListener('DOMContentLoaded', function() {
  const parentUsernameInput = document.getElementById('username');
  if (parentUsernameInput) {
    parentUsernameInput.addEventListener('blur', validateParentUsername);
  }
});
