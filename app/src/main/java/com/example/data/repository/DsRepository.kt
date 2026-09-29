package com.example.data.repository

import com.example.data.model.*

object DsRepository {

  val modules: List<CurriculumModule> = listOf(
    CurriculumModule(
      id = 1,
      level = 1,
      title = "Programming Foundations",
      iconName = "Memory",
      description = "Data organization, memory stack vs heap, references, pointers, and Abstract Data Types (ADTs).",
      lessonsCount = 4,
      estimatedMinutes = 35,
      isAdvanced = false,
      lessons = listOf(
        Lesson(
          id = "foundations-1",
          moduleId = 1,
          title = "What is a Data Structure?",
          summary = "Understanding data arrangement, memory allocation, and why choosing the right structure matters.",
          concept = "A data structure is a specialized format for organizing, processing, retrieving, and storing data. It dictates how elements relate to each other in memory and the computational cost (time and space) of fundamental operations like access, search, insertion, and deletion.",
          visualDescription = "Contrasting contiguous array blocks vs scattered heap nodes linked by pointer addresses.",
          codeSnippets = mapOf(
            ProgrammingLanguage.PYTHON to "# Primitive vs Compound Data Structure\nx = 42\ndata = [10, 20, 30, 40]  # Dynamic contiguous list\nprint(f'Length: {len(data)}, Element at 0: {data[0]}')",
            ProgrammingLanguage.JAVA to "public class MemoryModel {\n    public static void main(String[] args) {\n        int stackPrimitive = 42;\n        int[] heapArray = new int[]{10, 20, 30, 40};\n        System.out.println(\"Heap object ref: \" + heapArray);\n    }\n}",
            ProgrammingLanguage.CPP to "#include <iostream>\nint main() {\n    int stackVal = 42;\n    int* heapPtr = new int[4]{10, 20, 30, 40};\n    std::cout << \"Address: \" << heapPtr << std::endl;\n    delete[] heapPtr;\n    return 0;\n}",
            ProgrammingLanguage.KOTLIN to "fun main() {\n    val stackVal = 42\n    val list = intArrayOf(10, 20, 30, 40)\n    println(\"Element 0: \${list[0]}\")\n}",
            ProgrammingLanguage.JAVASCRIPT to "const stackVal = 42;\nconst arr = [10, 20, 30, 40];\nconsole.log(\"Index 0: \" + arr[0]);"
          ),
          timeComplexity = "Access: O(1) in arrays, O(n) in linked lists",
          spaceComplexity = "O(n) auxiliary",
          realWorldApplications = listOf("OS Process memory buffers", "File system directories", "Network packet queues"),
          commonMistakes = listOf("Confusing abstract interface with concrete implementation", "Ignoring memory overhead of pointers/references"),
          edgeCases = listOf("Empty structures (size = 0)", "Single element structures", "Memory exhaustion under allocation limits"),
          interviewTips = "Always clarify memory constraints and expected access-to-mutation ratios before proposing a data structure."
        )
      )
    ),
    CurriculumModule(
      id = 2,
      level = 2,
      title = "Arrays & Memory Layout",
      iconName = "Array",
      description = "Contiguous memory, 0-indexed arithmetic, static vs dynamic arrays, two pointers, and sliding window.",
      lessonsCount = 5,
      estimatedMinutes = 45,
      isAdvanced = false,
      lessons = listOf(
        Lesson(
          id = "array-1",
          moduleId = 2,
          title = "Array Fundamentals & Index Arithmetic",
          summary = "Why array access is O(1): Base_Address + Index * Element_Size.",
          concept = "Arrays store elements in strictly adjacent memory locations. Because each element takes a uniform size (e.g. 4 bytes for 32-bit integers), the CPU computes the direct hardware RAM address instantly in O(1) time without traversing preceding elements.",
          visualDescription = "┌─────┬─────┬─────┬─────┐\n│ 10  │ 20  │ 30  │ 40  │\n└─────┴─────┴─────┴─────┘\n  0     1     2     3",
          codeSnippets = mapOf(
            ProgrammingLanguage.PYTHON to "def linear_search(arr, target):\n    for i, val in enumerate(arr):\n        if val == target:\n            return i  # Found at index i\n    return -1  # Not found\n\nnumbers = [12, 25, 31, 44, 58]\nidx = linear_search(numbers, 44)\nprint(f'Found 44 at index: {idx}')",
            ProgrammingLanguage.JAVA to "public class ArrayOps {\n    public static int insertAt(int[] arr, int size, int index, int value) {\n        for (int i = size; i > index; i--) {\n            arr[i] = arr[i - 1]; // Shift right\n        }\n        arr[index] = value;\n        return size + 1;\n    }\n}",
            ProgrammingLanguage.CPP to "int search(int arr[], int n, int target) {\n    for(int i = 0; i < n; i++) {\n        if(arr[i] == target) return i;\n    }\n    return -1;\n}",
            ProgrammingLanguage.KOTLIN to "fun findMax(arr: IntArray): Int {\n    var maxVal = arr[0]\n    for (num in arr) {\n        if (num > maxVal) maxVal = num\n    }\n    return maxVal\n}",
            ProgrammingLanguage.JAVASCRIPT to "const arr = [12, 25, 31, 44, 58];\nconst search = (target) => arr.indexOf(target);"
          ),
          timeComplexity = "Access: O(1) | Search: O(n) | Insert/Delete at end: O(1)* | Insert at index 0: O(n)",
          spaceComplexity = "O(1) extra space",
          realWorldApplications = listOf("Display framebuffers (pixel grids)", "Audio sample buffers", "Matrix computations in Machine Learning"),
          commonMistakes = listOf("Off-by-one errors in loop boundaries (arr.length vs arr.length - 1)", "Forgetting shifting cost on middle insertion"),
          edgeCases = listOf("Empty array", "Index out of bounds", "Negative index references"),
          interviewTips = "When searching in a sorted array, always mention Binary Search O(log n) instead of linear scan O(n)."
        )
      )
    ),
    CurriculumModule(
      id = 3,
      level = 3,
      title = "Strings & Character Arrays",
      iconName = "String",
      description = "ASCII/Unicode encodings, palindromes, pattern matching, anagrams, and sliding window string parsing.",
      lessonsCount = 4,
      estimatedMinutes = 40,
      isAdvanced = false
    ),
    CurriculumModule(
      id = 4,
      level = 4,
      title = "Linked Lists",
      iconName = "LinkedList",
      description = "Node architecture, pointers, singly/doubly/circular lists, in-place reversal, and cycle detection.",
      lessonsCount = 6,
      estimatedMinutes = 55,
      isAdvanced = false,
      lessons = listOf(
        Lesson(
          id = "ll-1",
          moduleId = 4,
          title = "Singly Linked List: Pointers & Nodes",
          summary = "Dynamically allocated nodes connected via reference pointers. Eliminates memory resizing overhead.",
          concept = "A Linked List represents non-contiguous memory where each node holds data and a reference (pointer) to the subsequent node. Insertion and deletion at known node positions operate in O(1) pointer reassignment without shifting elements.",
          visualDescription = "HEAD -> [10 | *] -> [20 | *] -> [30 | NULL]",
          codeSnippets = mapOf(
            ProgrammingLanguage.PYTHON to "class Node:\n    def __init__(self, val):\n        self.val = val\n        self.next = None\n\n# Reverse in-place O(n) time, O(1) space\ndef reverse_list(head):\n    prev, curr = None, head\n    while curr:\n        nxt = curr.next\n        curr.next = prev\n        prev = curr\n        curr = nxt\n    return prev",
            ProgrammingLanguage.JAVA to "class Node {\n    int val;\n    Node next;\n    Node(int v) { this.val = v; }\n}\npublic Node reverse(Node head) {\n    Node prev = null, curr = head;\n    while (curr != null) {\n        Node nxt = curr.next;\n        curr.next = prev;\n        prev = curr;\n        curr = nxt;\n    }\n    return prev;\n}",
            ProgrammingLanguage.CPP to "struct Node {\n    int val;\n    Node* next;\n    Node(int v) : val(v), next(nullptr) {}\n};\nNode* reverseList(Node* head) {\n    Node* prev = nullptr;\n    Node* curr = head;\n    while(curr) {\n        Node* nxt = curr->next;\n        curr->next = prev;\n        prev = curr;\n        curr = nxt;\n    }\n    return prev;\n}",
            ProgrammingLanguage.KOTLIN to "class ListNode(var value: Int, var next: ListNode? = null)\n\nfun reverse(head: ListNode?): ListNode? {\n    var prev: ListNode? = null\n    var curr = head\n    while (curr != null) {\n        val nxt = curr.next\n        curr.next = prev\n        prev = curr\n        curr = nxt\n    }\n    return prev\n}",
            ProgrammingLanguage.JAVASCRIPT to "class Node {\n  constructor(val) { this.val = val; this.next = null; }\n}"
          ),
          timeComplexity = "Prepend: O(1) | Append: O(1) with tail ref | Search: O(n) | Delete given node: O(1)",
          spaceComplexity = "O(1) auxiliary",
          realWorldApplications = listOf("Blockchain transaction chaining", "Music playlist track queue", "Undo/Redo history buffers"),
          commonMistakes = listOf("Losing the reference to the rest of the list when modifying pointers", "NullPointerDereference on tail.next"),
          edgeCases = listOf("Null head (empty list)", "Single-node list", "Cycles in the list"),
          interviewTips = "Use Floyd's Cycle-Finding Algorithm (Tortoise and Hare fast & slow pointers) to detect cycles in O(n) time and O(1) space."
        )
      )
    ),
    CurriculumModule(
      id = 5,
      level = 5,
      title = "Stacks (LIFO)",
      iconName = "Stack",
      description = "Last In, First Out architecture, push/pop/peek operations, expression evaluation, and monotonic stacks.",
      lessonsCount = 5,
      estimatedMinutes = 45,
      isAdvanced = false
    ),
    CurriculumModule(
      id = 6,
      level = 6,
      title = "Queues (FIFO)",
      iconName = "Queue",
      description = "First In, First Out architecture, circular queues, deques, and asynchronous task scheduling.",
      lessonsCount = 5,
      estimatedMinutes = 45,
      isAdvanced = false
    ),
    CurriculumModule(
      id = 7,
      level = 7,
      title = "Hash Tables & Hashing",
      iconName = "HashTable",
      description = "Hash functions, buckets, load factor, collisions: separate chaining vs linear/quadratic probing.",
      lessonsCount = 6,
      estimatedMinutes = 55,
      isAdvanced = false
    ),
    CurriculumModule(
      id = 8,
      level = 8,
      title = "Recursion & Call Stack",
      iconName = "Recursion",
      description = "Base cases, recursive branching, call stack unwinding, recursion trees, and divide-and-conquer.",
      lessonsCount = 5,
      estimatedMinutes = 50,
      isAdvanced = false
    ),
    CurriculumModule(
      id = 9,
      level = 9,
      title = "Trees & Hierarchical Structures",
      iconName = "Tree",
      description = "Root, leaves, subtrees, height vs depth, full/complete/balanced trees, and traversals (In, Pre, Post, Level).",
      lessonsCount = 6,
      estimatedMinutes = 60,
      isAdvanced = false
    ),
    CurriculumModule(
      id = 10,
      level = 10,
      title = "Binary Search Trees (BST)",
      iconName = "BST",
      description = "BST ordering invariant: Left < Root < Right. Search, insertion, node deletion with successor replacement.",
      lessonsCount = 6,
      estimatedMinutes = 55,
      isAdvanced = false
    ),
    CurriculumModule(
      id = 11,
      level = 11,
      title = "Heaps (Min & Max)",
      iconName = "Heap",
      description = "Complete binary tree property, array indexing (2i+1, 2i+2), heapify up/down, extract-max, and Heap Sort.",
      lessonsCount = 5,
      estimatedMinutes = 50,
      isAdvanced = false
    ),
    CurriculumModule(
      id = 12,
      level = 12,
      title = "Priority Queues",
      iconName = "PriorityQueue",
      description = "Prioritized scheduling, Dijkstra algorithm optimization, and Huffman coding tree construction.",
      lessonsCount = 4,
      estimatedMinutes = 40,
      isAdvanced = false
    ),
    CurriculumModule(
      id = 13,
      level = 13,
      title = "Tries (Prefix Trees)",
      iconName = "Trie",
      description = "Prefix-based tree search, character edges, fast string dictionary lookup, and autocomplete engines.",
      lessonsCount = 4,
      estimatedMinutes = 45,
      isAdvanced = false
    ),
    CurriculumModule(
      id = 14,
      level = 14,
      title = "Graphs & Traversals (BFS / DFS)",
      iconName = "Graph",
      description = "Vertices, edges, directed vs undirected, adjacency lists/matrices, Breadth-First & Depth-First Search.",
      lessonsCount = 7,
      estimatedMinutes = 65,
      isAdvanced = false
    ),
    CurriculumModule(
      id = 15,
      level = 15,
      title = "Advanced Trees",
      iconName = "AdvancedTree",
      description = "Self-balancing AVL Trees (rotations), Red-Black Trees, Segment Trees, and Fenwick Trees (Binary Indexed Trees).",
      lessonsCount = 6,
      estimatedMinutes = 65,
      isAdvanced = true
    ),
    CurriculumModule(
      id = 16,
      level = 16,
      title = "Advanced Data Structures",
      iconName = "AdvancedDS",
      description = "Disjoint Set Union (Union-Find), LRU Cache, Bloom Filters, Skip Lists, B-Trees, and Suffix Trees.",
      lessonsCount = 6,
      estimatedMinutes = 70,
      isAdvanced = true
    ),
    CurriculumModule(
      id = 17,
      level = 17,
      title = "Complexity Analysis (Big-O)",
      iconName = "Complexity",
      description = "Asymptotic notations: Big-O, Big-Omega, Big-Theta, growth orders, best/average/worst case, and amortized time.",
      lessonsCount = 5,
      estimatedMinutes = 45,
      isAdvanced = false
    ),
    CurriculumModule(
      id = 18,
      level = 18,
      title = "Problem Solving Patterns",
      iconName = "Patterns",
      description = "Two Pointers, Sliding Window, Monotonic Stack, Top-K Elements, Fast & Slow Pointers, and BFS Level-order.",
      lessonsCount = 6,
      estimatedMinutes = 60,
      isAdvanced = false
    ),
    CurriculumModule(
      id = 19,
      level = 19,
      title = "Technical Interview Preparation",
      iconName = "Interview",
      description = "Clarifying requirements, test-case brainstorming, constraint math, time/space tradeoffs, and dry-run execution.",
      lessonsCount = 5,
      estimatedMinutes = 55,
      isAdvanced = false
    ),
    CurriculumModule(
      id = 20,
      level = 20,
      title = "Advanced Challenges & Systems",
      iconName = "Challenge",
      description = "High-throughput caches, persistent data structures, concurrent queue concurrency, and real-world engine design.",
      lessonsCount = 5,
      estimatedMinutes = 60,
      isAdvanced = true
    )
  )

  val complexityMatrix: List<ComplexityInfo> = listOf(
    ComplexityInfo("Array", "O(n)", "O(n)", "O(n)", "O(1)", "O(n)", "Contiguous memory; instant O(1) index access."),
    ComplexityInfo("Linked List", "O(n)", "O(1)*", "O(1)*", "O(n)", "O(n)", "*O(1) when node pointer is already known."),
    ComplexityInfo("Stack (Array/List)", "O(n)", "O(1)", "O(1)", "O(n)", "O(n)", "Strict LIFO policy via Push/Pop."),
    ComplexityInfo("Queue (FIFO)", "O(n)", "O(1)", "O(1)", "O(n)", "O(n)", "Enqueue at rear, dequeue from front."),
    ComplexityInfo("Hash Table", "O(1)*", "O(1)*", "O(1)*", "N/A", "O(n)", "*Average O(1). Degrades to O(n) on catastrophic collisions."),
    ComplexityInfo("Binary Search Tree", "O(log n)*", "O(log n)*", "O(log n)*", "N/A", "O(n)", "*Degrades to O(n) if unbalanced (skewed linked list)."),
    ComplexityInfo("AVL / Red-Black Tree", "O(log n)", "O(log n)", "O(log n)", "N/A", "O(n)", "Guaranteed O(log n) via self-balancing rotations."),
    ComplexityInfo("Binary Heap", "O(n)", "O(log n)", "O(log n)", "O(1) peek", "O(n)", "O(1) access to min/max; O(log n) extract."),
    ComplexityInfo("Trie", "O(k)", "O(k)", "O(k)", "N/A", "O(N * k)", "k = length of key word. Outstanding for prefix queries."),
    ComplexityInfo("Graph (Adj List)", "O(V + E)", "O(1)", "O(E)", "N/A", "O(V + E)", "V = Vertices, E = Edges.")
  )

  val dailyChallenge: CodingChallenge = CodingChallenge(
    id = "daily-1",
    title = "Invert a Binary Tree",
    difficulty = DifficultyLevel.INTERMEDIATE,
    topic = "Trees & Recursion",
    estimatedMinutes = 15,
    points = 50,
    problemStatement = "Given the root of a binary tree, invert the tree (swap the left and right subtrees of every node recursively), and return its root.",
    examples = listOf(
      "Input: root = [4, 2, 7, 1, 3, 6, 9]" to "Output: [4, 7, 2, 9, 6, 3, 1]",
      "Input: root = [2, 1, 3]" to "Output: [2, 3, 1]"
    ),
    constraints = listOf("The number of nodes is in range [0, 100]", "-100 <= Node.val <= 100"),
    hint = "Traverse the tree using postorder or preorder recursion. For each node, swap left and right pointers.",
    initialCode = mapOf(
      ProgrammingLanguage.PYTHON to "class TreeNode:\n    def __init__(self, val=0, left=None, right=None):\n        self.val = val\n        self.left = left\n        self.right = right\n\ndef invertTree(root):\n    # Write your solution here\n    if not root:\n        return None\n    root.left, root.right = invertTree(root.right), invertTree(root.left)\n    return root",
      ProgrammingLanguage.JAVA to "public TreeNode invertTree(TreeNode root) {\n    if (root == null) return null;\n    TreeNode temp = root.left;\n    root.left = invertTree(root.right);\n    root.right = invertTree(temp);\n    return root;\n}",
      ProgrammingLanguage.CPP to "TreeNode* invertTree(TreeNode* root) {\n    if(!root) return nullptr;\n    std::swap(root->left, root->right);\n    invertTree(root->left);\n    invertTree(root->right);\n    return root;\n}",
      ProgrammingLanguage.KOTLIN to "fun invertTree(root: TreeNode?): TreeNode? {\n    if (root == null) return null\n    val temp = root.left\n    root.left = invertTree(root.right)\n    root.right = invertTree(temp)\n    return root\n}",
      ProgrammingLanguage.JAVASCRIPT to "function invertTree(root) {\n    if (!root) return null;\n    [root.left, root.right] = [invertTree(root.right), invertTree(root.left)];\n    return root;\n}"
    ),
    solutionCode = mapOf(
      ProgrammingLanguage.PYTHON to "def invertTree(root):\n    if not root:\n        return None\n    root.left, root.right = invertTree(root.right), invertTree(root.left)\n    return root"
    ),
    testCases = listOf(
      "[4,2,7,1,3,6,9]" to "[4,7,2,9,6,3,1]",
      "[2,1,3]" to "[2,3,1]",
      "[]" to "[]"
    ),
    complexityExplanation = "Time: O(n) where n is total nodes visited once. Space: O(h) recursion stack frames where h is tree height."
  )

  val sampleChallenges: List<CodingChallenge> = listOf(
    dailyChallenge,
    CodingChallenge(
      id = "c-2",
      title = "Valid Parentheses",
      difficulty = DifficultyLevel.BEGINNER,
      topic = "Stacks",
      estimatedMinutes = 10,
      points = 30,
      problemStatement = "Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.",
      examples = listOf(
        "Input: s = '()[]{}'" to "Output: true",
        "Input: s = '(]'" to "Output: false"
      ),
      constraints = listOf("1 <= s.length <= 10^4", "s consists of parentheses only"),
      hint = "Use a stack. Push opening brackets, and when encountering a closing bracket, verify it matches the popped element.",
      initialCode = mapOf(
        ProgrammingLanguage.PYTHON to "def isValid(s: str) -> bool:\n    stack = []\n    mapping = {')': '(', '}': '{', ']': '['}\n    for char in s:\n        if char in mapping:\n            top = stack.pop() if stack else '#'\n            if mapping[char] != top:\n                return False\n        else:\n            stack.append(char)\n    return not stack"
      ),
      solutionCode = emptyMap(),
      testCases = listOf("()[]{}" to "true", "(]" to "false"),
      complexityExplanation = "Time Complexity: O(n), Space Complexity: O(n) for stack storage."
    ),
    CodingChallenge(
      id = "c-3",
      title = "Detect Cycle in a Linked List",
      difficulty = DifficultyLevel.INTERMEDIATE,
      topic = "Linked Lists",
      estimatedMinutes = 15,
      points = 45,
      problemStatement = "Given head, the head of a linked list, determine if the linked list has a cycle in it using O(1) memory.",
      examples = listOf("Input: head = [3,2,0,-4], pos = 1" to "Output: true"),
      constraints = listOf("Nodes in list [0, 10^4]", "-10^5 <= Node.val <= 10^5"),
      hint = "Floyd's Tortoise and Hare algorithm: slow moves 1 step, fast moves 2 steps. If they collide, a cycle exists.",
      initialCode = mapOf(
        ProgrammingLanguage.PYTHON to "def hasCycle(head) -> bool:\n    slow, fast = head, head\n    while fast and fast.next:\n        slow = slow.next\n        fast = fast.next.next\n        if slow == fast:\n            return True\n    return False"
      ),
      solutionCode = emptyMap(),
      testCases = listOf("Cycle exists" to "true", "Linear list" to "false"),
      complexityExplanation = "Time Complexity: O(n), Space Complexity: O(1) auxiliary."
    )
  )

  val miniProjects: List<MiniProject> = listOf(
    MiniProject(
      id = "proj-1",
      title = "Browser History Navigator",
      subtitle = "Two-Stack Back/Forward Architecture",
      primaryDs = "Stack",
      difficulty = DifficultyLevel.BEGINNER,
      description = "Simulate modern web browser navigation with visit(url), back(steps), and forward(steps) using two coordinated stacks.",
      architecture = "BackStack maintains visited pages. ForwardStack captures pages unvisited during backward steps. Visiting clears ForwardStack.",
      keyOperations = listOf("visit(url: String)", "back(steps: Int): String", "forward(steps: Int): String"),
      interactiveSimulationHint = "Try visiting google.com -> youtube.com -> github.com, then clicking Back twice!"
    ),
    MiniProject(
      id = "proj-2",
      title = "LRU Cache (Least Recently Used)",
      subtitle = "High-Performance O(1) Eviction",
      primaryDs = "Hash Map + Doubly Linked List",
      difficulty = DifficultyLevel.ADVANCED,
      description = "Design a data structure that follows the constraints of a Least Recently Used (LRU) cache with O(1) get and put operations.",
      architecture = "Hash map provides instant O(1) node lookup. Doubly linked list maintains access recency order (Head = MRU, Tail = LRU).",
      keyOperations = listOf("get(key: Int): Int", "put(key: Int, value: Int)"),
      interactiveSimulationHint = "Fill the cache capacity (3 items) and see the oldest item instantly evicted on adding a 4th."
    ),
    MiniProject(
      id = "proj-3",
      title = "Search Autocomplete Engine",
      subtitle = "Fast Trie Prefix Recommendation",
      primaryDs = "Trie (Prefix Tree)",
      difficulty = DifficultyLevel.INTERMEDIATE,
      description = "Index dictionary words and return ranked suggestions matching any typed query prefix in real-time.",
      architecture = "Each node stores 26 character pointers and an isEndOfWord flag. DFS from the prefix terminal node gathers words.",
      keyOperations = listOf("insert(word: String)", "searchPrefix(prefix: String): List<String>"),
      interactiveSimulationHint = "Type 'pro' to watch the Trie traverse to p -> r -> o and extract 'program', 'progress', 'project'."
    ),
    MiniProject(
      id = "proj-4",
      title = "Operating System Task Queue",
      subtitle = "Round-Robin & Priority Dispatcher",
      primaryDs = "Circular Queue & Priority Queue",
      difficulty = DifficultyLevel.INTERMEDIATE,
      description = "Simulate an OS CPU scheduler executing tasks with time-slices and priority preemption.",
      architecture = "Circular array buffer handles FIFO round-robin; Min-Heap priority queue manages emergency high-priority interrupts.",
      keyOperations = listOf("enqueueTask(task)", "dispatchNext()", "preemptHighPriority()"),
      interactiveSimulationHint = "Add tasks with priorities 1 to 5 and observe the scheduler execute top priority tasks first."
    ),
    MiniProject(
      id = "proj-5",
      title = "Social Network Friend Graph",
      subtitle = "Degree of Separation & Mutuals",
      primaryDs = "Adjacency List Graph + BFS",
      difficulty = DifficultyLevel.ADVANCED,
      description = "Build a bidirectional social graph to compute degrees of separation (shortest path) and suggest mutual friends.",
      architecture = "Map of user IDs to set of neighbor connections. Breadth-First Search finds the shortest connection path between two users.",
      keyOperations = listOf("addFriendship(u1, u2)", "getDegreesOfSeparation(u1, u2)", "suggestMutualFriends(user)"),
      interactiveSimulationHint = "Find the path connecting Alice to David via Bob and Charlie in 3 hops."
    )
  )

  val sampleQuiz: List<QuizQuestion> = listOf(
    QuizQuestion(
      id = "q-1",
      question = "What is the worst-case time complexity of searching in an unbalanced Binary Search Tree?",
      options = listOf("O(1)", "O(log n)", "O(n)", "O(n log n)"),
      correctIndex = 2,
      explanation = "When values are inserted in sorted order, a standard BST degenerates into a linear linked list with height n, making search O(n)."
    ),
    QuizQuestion(
      id = "q-2",
      question = "Which data structure follows the Last In, First Out (LIFO) access pattern?",
      options = listOf("Queue", "Stack", "Priority Queue", "Deque"),
      correctIndex = 1,
      explanation = "A Stack strictly enforces LIFO: the most recently pushed element is the first one retrieved by pop()."
    ),
    QuizQuestion(
      id = "q-3",
      question = "Why is direct array element access O(1)?",
      options = listOf(
        "The CPU iterates through cached items",
        "Memory address is calculated in 1 step: Base + Index * Size",
        "Arrays use hashing internally",
        "Compilers store all elements at the same pointer"
      ),
      correctIndex = 1,
      explanation = "Because arrays are contiguous in RAM, the hardware adds index * element_size directly to the base pointer in a single CPU cycle."
    ),
    QuizQuestion(
      id = "q-4",
      question = "What is the average time complexity of lookups in a well-distributed Hash Table?",
      options = listOf("O(1)", "O(log n)", "O(n)", "O(n²)"),
      correctIndex = 0,
      explanation = "With a good hash function and low load factor (< 0.75), bucket collisions are minimal, providing expected O(1) lookups."
    ),
    QuizQuestion(
      id = "q-5",
      question = "Which traversal order on a Binary Search Tree (BST) visits nodes in strictly ascending numerical order?",
      options = listOf("Preorder (Root -> Left -> Right)", "Inorder (Left -> Root -> Right)", "Postorder (Left -> Right -> Root)", "Level-order (BFS)"),
      correctIndex = 1,
      explanation = "Inorder traversal visits all smaller elements (Left subtree) first, then the Root, then all larger elements (Right subtree)."
    )
  )

  val flashcards: List<Flashcard> = listOf(
    Flashcard("fc-1", "Terminology", "What does LIFO stand for?", "Last In, First Out (Stack principle)."),
    Flashcard("fc-2", "Terminology", "What does FIFO stand for?", "First In, First Out (Queue principle)."),
    Flashcard("fc-3", "Complexity", "What is the time complexity of binary search?", "O(log n) time, requiring sorted data."),
    Flashcard("fc-4", "Pointers", "What happens when a node loses its reference in a linked list?", "It becomes unreachable and is collected by garbage collection (or leaks in C/C++)."),
    Flashcard("fc-5", "Hashing", "What is Hash Collision?", "When two distinct keys produce the exact same bucket hash index."),
    Flashcard("fc-6", "Trees", "What is the height of a balanced binary tree with N nodes?", "O(log₂ N), where each level doubles the capacity."),
    Flashcard("fc-7", "Heaps", "In a 0-indexed array heap, where are children of index i located?", "Left child = 2i + 1, Right child = 2i + 2. Parent = (i - 1) / 2.")
  )

  val glossaryTerms: List<GlossaryTerm> = listOf(
    GlossaryTerm("Node", "Foundations", "The fundamental building block containing a data payload and one or more reference pointers to other nodes.", "Node(value, next)", "●──→"),
    GlossaryTerm("Pointer / Reference", "Memory", "A variable holding the hexadecimal hardware memory address where another value or node is located in RAM.", "0x7FFEE4", "*ptr"),
    GlossaryTerm("Big-O Notation", "Complexity", "Mathematical upper bound describing the limiting behavior of an algorithm's execution time or space as input size approaches infinity.", "O(f(n))", "O(n log n)"),
    GlossaryTerm("Head", "Linked Lists", "A pointer referencing the first node in a linked list structure.", "head -> [10]", "HEAD"),
    GlossaryTerm("Tail", "Linked Lists / Queues", "A pointer referencing the final node in a linked list or queue.", "tail -> [99]", "TAIL"),
    GlossaryTerm("Root", "Trees", "The topmost single node in a hierarchical tree data structure having no parent.", "root -> (40)", "ROOT"),
    GlossaryTerm("Leaf", "Trees", "Any tree node that has zero child nodes (left = null, right = null).", "deg(v) = 0", "LEAF"),
    GlossaryTerm("Load Factor", "Hashing", "Ratio of stored items to hash table capacity (n / k). When it exceeds ~0.75, table resizes and rehashes.", "α = n / k", "75% full"),
    GlossaryTerm("Inversion", "Trees", "Swapping the left and right pointers of every node in a binary tree.", "left <-> right", "Mirror image"),
    GlossaryTerm("Adjacency List", "Graphs", "A collection of lists or arrays used to represent a graph where each vertex lists its neighboring connected vertices.", "adj[u] = [v1, v2]", "U -> {V1, V2}")
  )
}
