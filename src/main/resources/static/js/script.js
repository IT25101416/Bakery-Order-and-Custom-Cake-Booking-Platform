// ===============================
// Sweet Crumbs Bakery JavaScript
// ===============================

document.addEventListener("DOMContentLoaded", function () {
    console.log("Sweet Crumbs Bakery website loaded");

    confirmDeleteAction();
    validateForms();
    autoCalculateOrderTotal();
    showSuccessMessage();
    highlightActiveNavLink();
    ratingMessage();
    searchTable();
    addScrollRevealAnimations();
    addButtonClickAnimation();
    animateCountUpNumbers();
    setupReviewInterface();
    setupSecretAdminLogin();
    setupManagementCenter();
});


// ===============================
// 1. Confirm Before Delete
// ===============================

function confirmDeleteAction() {
    const deleteButtons = document.querySelectorAll(".delete");

    deleteButtons.forEach(function (button) {
        button.addEventListener("click", function (event) {
            const confirmDelete = confirm("Are you sure you want to delete this record?");

            if (!confirmDelete) {
                event.preventDefault();
            }
        });
    });
}


// ===============================
// 2. Basic Form Validation
// ===============================

function validateForms() {
    const forms = document.querySelectorAll("form");

    forms.forEach(function (form) {
        form.addEventListener("submit", function (event) {
            const inputs = form.querySelectorAll("input[required], textarea[required], select[required]");
            let isValid = true;

            inputs.forEach(function (input) {
                if (input.value.trim() === "") {
                    isValid = false;
                    input.style.border = "2px solid red";
                } else {
                    input.style.border = "1px solid #ccc";
                }
            });

            if (!isValid) {
                event.preventDefault();
                alert("Please fill all required fields.");
            }
        });
    });
}


// ===============================
// 3. Auto Calculate Order Total
// ===============================

function autoCalculateOrderTotal() {
    const quantityInput = document.getElementById("quantity");
    const priceInput = document.getElementById("price");
    const totalInput = document.getElementById("totalPrice");

    if (quantityInput && priceInput && totalInput) {
        function calculateTotal() {
            const quantity = parseInt(quantityInput.value) || 0;
            const price = parseFloat(priceInput.value) || 0;
            const total = quantity * price;

            totalInput.value = total.toFixed(2);
        }

        quantityInput.addEventListener("input", calculateTotal);
        priceInput.addEventListener("input", calculateTotal);
    }
}


// ===============================
// 4. Show Success Message
// ===============================

function showSuccessMessage() {
    const forms = document.querySelectorAll("form");

    forms.forEach(function (form) {
        form.addEventListener("submit", function () {
            const button = form.querySelector("button");

            if (button) {
                button.innerText = "Processing...";
                button.disabled = true;

                setTimeout(function () {
                    button.disabled = false;
                }, 3000);
            }
        });
    });
}


// ===============================
// 5. Highlight Active Navigation Link
// ===============================

function highlightActiveNavLink() {
    const currentUrl = window.location.pathname;
    const navLinks = document.querySelectorAll(".navbar a");

    navLinks.forEach(function (link) {
        if (link.getAttribute("href") === currentUrl) {
            link.classList.add("active-link");
        }
    });
}


// ===============================
// 6. Review Rating Message
// ===============================

function ratingMessage() {
    const ratingSelect = document.getElementById("rating");
    const messageBox = document.getElementById("ratingMessage");

    if (ratingSelect && messageBox) {
        ratingSelect.addEventListener("change", function () {
            const rating = parseInt(ratingSelect.value);

            if (rating === 5) {
                messageBox.innerText = "Excellent! Thank you for your amazing feedback.";
            } else if (rating === 4) {
                messageBox.innerText = "Great! We are happy you liked it.";
            } else if (rating === 3) {
                messageBox.innerText = "Good. We will try to improve more.";
            } else if (rating === 2) {
                messageBox.innerText = "Sorry. We will improve our service.";
            } else if (rating === 1) {
                messageBox.innerText = "Very sorry. Please tell us what went wrong.";
            } else {
                messageBox.innerText = "";
            }
        });
    }
}


// ===============================
// 7. Search Table Data
// ===============================

function searchTable() {
    const searchInput = document.getElementById("tableSearch");

    if (!searchInput) {
        return;
    }

    const table = document.querySelector("table");
    const productCards = document.querySelectorAll(".product-card, .review-display-card");

    searchInput.addEventListener("keyup", function () {
        const searchValue = searchInput.value.toLowerCase();

        if (table) {
            const rows = table.querySelectorAll("tr");

            for (let i = 1; i < rows.length; i++) {
                const rowText = rows[i].innerText.toLowerCase();

                if (rowText.includes(searchValue)) {
                    rows[i].style.display = "";
                } else {
                    rows[i].style.display = "none";
                }
            }
        }

        if (productCards.length > 0) {
            productCards.forEach(function (card) {
                const cardText = card.innerText.toLowerCase();

                if (cardText.includes(searchValue)) {
                    card.style.display = "block";
                } else {
                    card.style.display = "none";
                }
            });
        }
    });
}


// ===============================
// 8. Show Password Toggle
// ===============================

function togglePassword(inputId) {
    const passwordInput = document.getElementById(inputId);

    if (passwordInput) {
        if (passwordInput.type === "password") {
            passwordInput.type = "text";
        } else {
            passwordInput.type = "password";
        }
    }
}


// ===============================
// 9. Preview Image Name
// ===============================

function showImageName() {
    const imageInput = document.getElementById("cakeImage");
    const imageName = document.getElementById("imageName");

    if (imageInput && imageName) {
        imageInput.addEventListener("change", function () {
            if (imageInput.files.length > 0) {
                imageName.innerText = "Selected image: " + imageInput.files[0].name;
            } else {
                imageName.innerText = "";
            }
        });
    }
}


// ===============================
// 10. Clear Form
// ===============================

function clearForm(formId) {
    const form = document.getElementById(formId);

    if (form) {
        form.reset();
    }
}


// ===============================
// 11. Scroll Reveal Animations
// ===============================

function addScrollRevealAnimations() {
    const animatedItems = document.querySelectorAll(
        ".category-section, .products-section, .dashboard-section, .form-container, .table-container, .product-card, .dashboard-card, .category-row span, table tr, .shop-intro-section, .intro-copy, .intro-visual, .intro-stats div, .item-showcase-section, .showcase-item, .moving-items-strip, .management-hero, .management-stat-card, .management-card, .management-flow-section, .flow-step"
    );

    if (animatedItems.length === 0) {
        return;
    }

    animatedItems.forEach(function (item, index) {
        item.classList.add("scroll-reveal");
        item.style.transitionDelay = Math.min(index * 45, 420) + "ms";
    });

    const observer = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
            if (entry.isIntersecting) {
                entry.target.classList.add("visible");
                observer.unobserve(entry.target);
            }
        });
    }, {
        threshold: 0.12
    });

    animatedItems.forEach(function (item) {
        observer.observe(item);
    });
}


// ===============================
// 12. Button Click Animation
// ===============================

function addButtonClickAnimation() {
    const clickableItems = document.querySelectorAll("button, .btn, .primary-btn, .secondary-btn, .order-btn, .action-btn, .edit-small, .delete-small");

    clickableItems.forEach(function (item) {
        item.addEventListener("mousedown", function () {
            item.classList.add("loading-click");
        });

        item.addEventListener("mouseup", function () {
            item.classList.remove("loading-click");
        });

        item.addEventListener("mouseleave", function () {
            item.classList.remove("loading-click");
        });
    });
}


// ===============================
// 13. Animated Count Numbers
// ===============================

function animateCountUpNumbers() {
    const counters = document.querySelectorAll(".count-up");

    if (counters.length === 0) {
        return;
    }

    const observer = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
            if (!entry.isIntersecting) {
                return;
            }

            const counter = entry.target;
            const target = parseInt(counter.getAttribute("data-count")) || 0;
            let current = 0;
            const step = Math.max(1, Math.ceil(target / 45));

            const timer = setInterval(function () {
                current += step;

                if (current >= target) {
                    current = target;
                    clearInterval(timer);
                }

                counter.innerText = current;
            }, 24);

            observer.unobserve(counter);
        });
    }, {
        threshold: 0.45
    });

    counters.forEach(function (counter) {
        observer.observe(counter);
    });
}

// ===============================
// 14. Custom Cake Studio Preview
// ===============================
function setupCustomCakeStudio() {
    const sizeSelect = document.getElementById("cakeSizeSelect");
    const flavorInput = document.getElementById("cakeFlavorInput");
    const dateInput = document.getElementById("cakeDateInput");
    const designInput = document.getElementById("designDescriptionInput");

    const previewSize = document.getElementById("previewSize");
    const previewFlavor = document.getElementById("previewFlavor");
    const previewDate = document.getElementById("previewDate");
    const previewDesign = document.getElementById("previewDesign");
    const chips = document.querySelectorAll(".quick-design-chips button");

    if (!sizeSelect || !flavorInput || !dateInput || !designInput) {
        return;
    }

    function updatePreview() {
        if (previewSize) {
            previewSize.innerText = sizeSelect.value || "Medium";
        }

        if (previewFlavor) {
            previewFlavor.innerText = flavorInput.value || "Chocolate";
        }

        if (previewDate) {
            previewDate.innerText = dateInput.value || "Select date";
        }

        if (previewDesign) {
            previewDesign.innerText = designInput.value || "Tell us your colours, theme, name, age, message, or photo cake idea.";
        }
    }

    [sizeSelect, flavorInput, dateInput, designInput].forEach(function (field) {
        field.addEventListener("input", updatePreview);
        field.addEventListener("change", updatePreview);
    });

    chips.forEach(function (chip) {
        chip.addEventListener("click", function () {
            designInput.value = chip.getAttribute("data-design") || "";
            designInput.focus();
            updatePreview();
        });
    });

    updatePreview();
}

if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", setupCustomCakeStudio);
} else {
    setupCustomCakeStudio();
}

// ===============================
// 15. Animated Review Interface
// ===============================
function setupReviewInterface() {
    const ratingSelect = document.getElementById("rating");
    const liveStars = document.querySelectorAll("#liveStars span");
    const previewStars = document.getElementById("previewStars");
    const nameInput = document.getElementById("reviewCustomerInput");
    const cakeInput = document.getElementById("reviewCakeInput");
    const commentInput = document.getElementById("reviewCommentInput");
    const previewReviewer = document.getElementById("previewReviewer");
    const previewCakeName = document.getElementById("previewCakeName");
    const previewReviewText = document.getElementById("previewReviewText");
    const reviewChips = document.querySelectorAll(".quick-review-chips button");

    if (!ratingSelect && !nameInput && !cakeInput && !commentInput) {
        return;
    }

    function updateStars() {
        const rating = parseInt(ratingSelect ? ratingSelect.value : "0") || 0;

        liveStars.forEach(function (star, index) {
            if (index < rating) {
                star.innerText = "★";
                star.classList.add("filled");
            } else {
                star.innerText = "☆";
                star.classList.remove("filled");
            }
        });

        if (previewStars) {
            let starsText = "";
            for (let i = 1; i <= 5; i++) {
                starsText += i <= rating ? "★" : "☆";
            }
            previewStars.innerText = starsText;
        }
    }

    function updateReviewPreview() {
        if (previewReviewer && nameInput) {
            previewReviewer.innerText = nameInput.value.trim() || "Customer Name";
        }

        if (previewCakeName && cakeInput) {
            previewCakeName.innerText = cakeInput.value.trim() || "Cake Name";
        }

        if (previewReviewText && commentInput) {
            previewReviewText.innerText = commentInput.value.trim() || "Your comment will appear here while you type.";
        }

        updateStars();
    }

    [ratingSelect, nameInput, cakeInput, commentInput].forEach(function (field) {
        if (field) {
            field.addEventListener("input", updateReviewPreview);
            field.addEventListener("change", updateReviewPreview);
        }
    });

    reviewChips.forEach(function (chip) {
        chip.addEventListener("click", function () {
            if (commentInput) {
                commentInput.value = chip.getAttribute("data-review") || "";
                commentInput.focus();
                updateReviewPreview();
            }
        });
    });

    updateReviewPreview();
}


// ===============================
// 16. Secret Admin Login
// ===============================
function setupSecretAdminLogin() {
    const loginCard = document.querySelector(".secret-login-card");
    const trigger = document.getElementById("secretAdminTrigger");
    const backButton = document.getElementById("backCustomerLogin");
    const adminPanel = document.getElementById("adminSecretPanel");

    if (!loginCard || !trigger || !adminPanel) {
        return;
    }

    let clickCount = 0;
    let resetTimer = null;

    function openAdminMode() {
        loginCard.classList.add("admin-mode");
        adminPanel.classList.add("show-secret-admin");
        const firstInput = adminPanel.querySelector("input");
        if (firstInput) {
            setTimeout(function () {
                firstInput.focus();
            }, 120);
        }
    }

    function closeAdminMode() {
        loginCard.classList.remove("admin-mode");
        adminPanel.classList.remove("show-secret-admin");
        clickCount = 0;
    }

    if (adminPanel.classList.contains("show-secret-admin")) {
        loginCard.classList.add("admin-mode");
    }

    trigger.addEventListener("click", function () {
        clickCount++;
        trigger.style.transform = "scale(1.12) rotate(" + (clickCount * 14) + "deg)";

        clearTimeout(resetTimer);
        resetTimer = setTimeout(function () {
            clickCount = 0;
            trigger.style.transform = "";
        }, 1400);

        if (clickCount >= 5) {
            openAdminMode();
            trigger.style.transform = "";
        }
    });

    if (backButton) {
        backButton.addEventListener("click", closeAdminMode);
    }

    document.addEventListener("keydown", function (event) {
        const key = event.key ? event.key.toLowerCase() : "";
        if (event.ctrlKey && event.shiftKey && key === "a") {
            openAdminMode();
        }
    });
}


// ===============================
// 17. Management Center Search + Entrance
// ===============================
function setupManagementCenter() {
    const searchInput = document.getElementById("managementSearch");
    const cards = document.querySelectorAll(".management-card");

    cards.forEach(function (card, index) {
        card.style.animationDelay = Math.min(index * 90, 540) + "ms";
        card.classList.add("management-card-ready");
    });

    if (!searchInput || cards.length === 0) {
        return;
    }

    searchInput.addEventListener("input", function () {
        const value = searchInput.value.toLowerCase().trim();

        cards.forEach(function (card) {
            const text = (card.innerText + " " + (card.getAttribute("data-management") || "")).toLowerCase();

            if (text.includes(value)) {
                card.classList.remove("hidden-management");
            } else {
                card.classList.add("hidden-management");
            }
        });
    });
}
