# Contributing to Zeni: The Travel Planner app

## Branching Strategy

We follow a simple branching strategy to ensure smooth collaboration and code integration. Below are the key branches and their purposes:

### Main Branch
- **`main`**: This is the production-ready branch. All code in this branch should be stable and thoroughly tested. It should only contain code that is ready for deployment or that is deployed.

### Development Branches
- **`x.0.0`**: This branch contains the longest updates. Includes a lot of new features and changes in comparison with every other branch. Any other branch should be merged into this one to apply news updates when necessary.
- **`x.y.0`**: This branch contains the updates for a minor version. It includes new features and changes that are not as big as the ones in the `x.0.0` branch.
- **`x.y.z`**: This branch contains the updates for a patch version. It includes bug fixes and minor changes that do not affect the overall functionality of the app.

## Workflow

1. **Creating a Branch**:
    ```sh
    git checkout -b <version-to-develop>
    ```

2. **Committing Changes**:
    - Write descriptive but concise commit messages.
    - One commit per feature or bug fix. Only when is executable.

3. **Pushing Changes**:
    ```sh
    git push origin <version-to-develop>
    ```

## Testing

- Ensure all new code is covered by tests. Only will be merged with `main` the code with tests passed.
- Run all tests before pushing changes.

Thank you for contributing to Zeni!