INSERT INTO public.users (username, password)
SELECT 'admin', '$2y$12$XWzFZx6j9UZ1z5JHDcZIFeByJgC8XtB1MHXPtQhrM9N/UQx8UwJ/2'
WHERE NOT EXISTS (SELECT 1 FROM public.users WHERE username = 'admin');

INSERT INTO public.user_role (user_id, role)
SELECT id, 1 FROM public.users WHERE username = 'admin'
AND NOT EXISTS (
    SELECT 1 FROM public.user_role
    WHERE user_id = (SELECT id FROM public.users WHERE username = 'admin') AND role = 1
);
