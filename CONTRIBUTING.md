# Contributing to Zeni Travel Planner

## Branching Strategy

We follow a simple branching strategy to ensure smooth collaboration and code integration. Below are the key branches and their purposes:

### Main Branch
- **`main`**: This is the production-ready branch. All code in this branch should be stable and thoroughly tested.

### Development Branch
- **`develop`**: This branch contains the latest development changes. It is the integration branch for features and fixes. All new features and bug fixes should be merged into this branch.

### Feature Branches
- **`feature/<feature-name>`**: These branches are used for developing new features. Each feature should have its own branch created from the `develop` branch. Once the feature is complete, it should be merged back into `develop`.

### Bugfix Branches
- **`bugfix/<bugfix-name>`**: These branches are used for fixing bugs. Each bug fix should have its own branch created from the `develop` branch. Once the bug fix is complete, it should be merged back into `develop`.

### Release Branches
- **`release/<version>`**: These branches are used for preparing a new production release. They are created from the `develop` branch when the `develop` branch is stable and ready for release. Once the release is ready, it should be merged into both `main` and `develop`.

### Hotfix Branches
- **`hotfix/<hotfix-name>`**: These branches are used for critical fixes that need to be applied to the `main` branch immediately. They are created from the `main` branch and, once complete, should be merged into both `main` and `develop`.

## Workflow

1. **Creating a Branch**:
    ```sh
    git checkout -b feature/<feature-name>
    ```

2. **Committing Changes**:
    - Write clear and concise commit messages.
    - Follow the commit message format: `type(scope): description`.

3. **Pushing Changes**:
    ```sh
    git push origin feature/<feature-name>
    ```

4. **Creating a Pull Request**:
    - Ensure your branch is up to date with `develop`.
    - Create a pull request against the `develop` branch.
    - Request reviews from team members.

5. **Merging Changes**:
    - Once approved, merge the pull request into `develop`.
    - Delete the feature branch after merging.

## Code Reviews

- All changes must be reviewed by at least one other team member.
- Reviewers should check for code quality, functionality, and adherence to the project's coding standards.

## Testing

- Ensure all new code is covered by tests.
- Run all tests before pushing changes.

Thank you for contributing to Zeni Travel Planner!